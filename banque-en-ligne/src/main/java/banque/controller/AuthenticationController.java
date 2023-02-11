package banque.controller;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.Image;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties.Tomcat.Resource;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.EntityResponse;
import org.springframework.util.StringUtils;
import banque.Event.OnRegistrationCompleteEvent;
import banque.entities.Client;
import banque.entities.Employe;
import banque.entities.FileUploadUtil;
import banque.entities.ImageResponse;
import banque.entities.ImageUtil;
import banque.entities.JwtResponse;
import banque.entities.RefreshToken;
import banque.entities.TokenRefreshRequest;
import banque.repositories.ClientRepository;
import banque.repositories.EmployeRepo;
import banque.security.JWTTokenProvider;
import banque.security.UserPrincipal;
import banque.services.AzureBlobAdapter;
import banque.services.ICustomerService;
import banque.services.IEmailVerificationTokenService;
import banque.services.IEmployeService;
import banque.services.IRefreshTokenService;
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthenticationController {
	
	private static final Logger logger=LogManager.getLogger(AuthenticationController.class);
	
	
	private final RestTemplate restTemplate= new RestTemplate();
	@Autowired
	IEmployeService agentService;
	
	@Autowired
	ICustomerService custService;
	
	@Autowired
	EmployeRepo employeRepo;
	
	@Autowired
	ClientRepository clientRepo;
	
	@Autowired
	JWTTokenProvider jwtTokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private IRefreshTokenService refreshTokenService;
	/*
	@Autowired
	IPasswordTokenService passwordTokenService;
	*/
	@Autowired
	private IEmailVerificationTokenService emailVerificationTokenService;
	
	@Autowired
	ApplicationEventPublisher eventPublisher;
	
	 @Autowired
	 AzureBlobAdapter azureAdapter;
	
	
	//@Autowired
	//MySimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler=new MySimpleUrlAuthenticationSuccessHandler(authenticationManager);

	 @GetMapping("/loginAgent")
	 @ResponseBody
	 public ResponseEntity<?> loginAgent(@RequestParam("email") String username,@RequestParam("password") String pass,HttpServletRequest request) throws Exception {
			int result=0;
			System.out.print("----razerarar------------->"+new UsernamePasswordAuthenticationToken(username, pass));

			
			result =authenticate(username,pass);
			
			if(result == 0) {
			
				Employe ag=employeRepo.findByEmail(username);

			Client cs=clientRepo.findByEmail(username);
			String token="";
			JwtResponse jwtResponse=null;
			RefreshToken refreshToken=null;
			
			UserPrincipal principal;
			

				//authenticationSuccessHandler.loginAgentNotification(ag, request);
				 principal=new UserPrincipal(ag);
				  token=jwtTokenProvider.generateToken(principal);
				  ag.setToken(token);
				  refreshToken=refreshTokenService.CreateRefreshToken(principal);
				  jwtResponse=new JwtResponse(token, refreshToken.getToken(), ag);
			


			
			
			return ResponseEntity.ok(jwtResponse);
			}
			else 
				return ResponseEntity.ok(result);
	 }
	 
	
	@GetMapping("/login")
	@ResponseBody
	public ResponseEntity<?> Login( @RequestParam("email") String username,@RequestParam("password") String pass,HttpServletRequest request) throws Exception {
		
	
		int result=0;
		
		
		result =authenticate(username,pass);
		
		if(result == 0) {
		
			Employe ag=employeRepo.findByEmail(username);

		Client cs=clientRepo.findByEmail(username);
		String token="";
		JwtResponse jwtResponse=null;
		RefreshToken refreshToken=null;
		
		UserPrincipal principal;
		
		if(ag != null) {
			//authenticationSuccessHandler.loginAgentNotification(ag, request);
			 principal=new UserPrincipal(ag);
			  token=jwtTokenProvider.generateToken(principal);
			  ag.setToken(token);
			  refreshToken=refreshTokenService.CreateRefreshToken(principal);
			  jwtResponse=new JwtResponse(token, refreshToken.getToken(), ag);
		}

		else if(cs != null){
			//authenticationSuccessHandler.loginCustomerNotification(cs,request);
		principal=new UserPrincipal(cs);
		token=jwtTokenProvider.generateToken(principal);
		cs.setToken(token);
		refreshToken=refreshTokenService.CreateRefreshToken(principal);
		jwtResponse=new JwtResponse(token, refreshToken.getToken(), cs);
		}
		
		
		return ResponseEntity.ok(jwtResponse);
		}
		else 
			return ResponseEntity.ok(result);
		
	}
	
	
	
	
	private int authenticate(String username, String pass) throws Exception{
		System.out.print("----razerarar------------->"+new UsernamePasswordAuthenticationToken(username, pass));

		try {
		SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, pass)));
		
			return 0;
			
		}
		catch (DisabledException ex) {
			return 1;
		}
		catch (BadCredentialsException ex) {
			
			return 2;
		}catch (Exception e) {
			return 3;
		}
		
	}
	
	
	@PostMapping("/refreshToken")
	public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) throws Exception{
		RefreshToken requestRefreshToken=request.getRefreshToken();
		RefreshToken tokenRefresh=refreshTokenService.getByToken(requestRefreshToken.getToken()).get();
		UserPrincipal principal=null;
		if(tokenRefresh != null && refreshTokenService.VerifyExpiration(tokenRefresh)) {
		
		if(tokenRefresh.getAgentToken() != null) {
			
			 principal=new UserPrincipal(tokenRefresh.getAgentToken());
		}

		else if(tokenRefresh.getCustomerToken() != null) {
			
			principal=new UserPrincipal(tokenRefresh.getCustomerToken());
		}
		
		
		String token=jwtTokenProvider.generateToken(principal);
		return ResponseEntity.ok(new JwtResponse(token,tokenRefresh.getToken()));
	}
		else {
			throw new Exception(requestRefreshToken.getToken()+" Refresh token is not in database!");
		}
			
	}
	@Autowired
	EmployeRepo empRep;
	@PostMapping("/addEmploye")
	@ResponseBody
	public Employe registerAgent(@RequestBody Employe agent) {
		
		//	agent.setImage(image);
			agentService.addAgent2(agent);
			empRep.save(agent);
	        eventPublisher.publishEvent(new OnRegistrationCompleteEvent( agent));
		
		return agent;
	}
	
	@Autowired
	ClientRepository customerRep;
	
	@PostMapping("/addImage")
	@ResponseBody
	public MultipartFile registerCustomer(@RequestParam Long id,@RequestParam("image") MultipartFile image) throws IOException, MessagingException {
		Client a = customerRep.findById(id).orElse(null);
		a.setImageData(ImageUtil.compressImage(image.getBytes()));

		customerRep.save(a);
 
       // FileUploadUtil.saveFile(uploadDir, fileName, image);
        custService.addImage(id,image);
		

		
		return image;
	}
	@GetMapping("/getImage")
	@ResponseBody
	public byte[] imageCustomer(@RequestParam Long id) throws IOException {
		Client a = customerRep.findById(id).orElse(null);
		byte[] image = a.getImageData();		
      
		 byte[] img = ImageUtil.decompressImage(image);

		
		return image;
	}
	
	
	@PostMapping("/addCustomer")
	@ResponseBody
	public Client registerCustomer(@RequestBody Client customer)  {
        custService.addClient2(customer);
         
        customerRep.save(customer);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent( customer));
		
		/*
		try {
			
			
			
			custService.addClient(customer,image);
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent( customer));
			
			
			
		} catch (Exception e) {
			System.out.print(e.getMessage());
			
		}*/
		
		return customer;
	}
	
	@GetMapping("/confirmRegistration/{token}")
	
	public void confirmAgentRegistration(@PathVariable("token") String token) {
		
		emailVerificationTokenService.ConfirmUserRegistration(token);
	}
	
	
	
	@GetMapping("/verifyEmail")
	@ResponseBody
	public Boolean verifyEmail(@RequestParam("email") String email) {
		if ((employeRepo.findByEmail(email)!= null) || (clientRepo.findByEmail(email)!= null)) {
			logger.info("Email exist");
			return true;
		}
		else 
			
			return false;
		
			
		}
	/*
	 @PostMapping("/upload")
	    public  ImageResponse uploadFile(@RequestParam(value = "imageFile", required = true) MultipartFile files)  {
		 
	        String name = azureAdapter.upload(files, "prefix");
	       // Map<String, String> result = new HashMap<>();
	        //result.put("key", name);
	        return new ImageResponse(name);
	    }
*/
}
