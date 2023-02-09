package banque.services;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import banque.entities.Client;
import banque.entities.Employe;
import banque.entities.RefreshToken;
import banque.repositories.ClientRepository;
import banque.repositories.EmployeRepo;
import banque.repositories.RefreshTokenRepository;
import banque.security.UserPrincipal;
@Service
public class RefreshTokenService implements IRefreshTokenService {
	
	
	@Value("${jwt.refreshTokenDurationMs}")
	private Long refreshTokenDurationMs;
	
	@Autowired
	RefreshTokenRepository refreshTokenRepository;
	
	@Autowired
	ClientRepository customerRepository;
	
	@Autowired
	EmployeRepo agentRepository;


	@Override
	public Optional<RefreshToken> getByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	@Override
	public RefreshToken CreateRefreshToken(UserPrincipal principal) {
		
		String username=principal.getUsername();
		//DeleteByUser(username);
		Employe Ag=agentRepository.findByEmail(username);
		Client customer=customerRepository.findByEmail(username);

		RefreshToken refreshToken=new RefreshToken();
		if(Ag != null) 
			refreshToken.setAgentToken(Ag);
		else if(customer != null) {
			refreshToken.setCustomerToken(customer);
	
		}
		
		refreshToken.setExpireDate(Instant.now().plusMillis(refreshTokenDurationMs));
		refreshToken.setToken(UUID.randomUUID().toString());
		
		
		refreshToken=refreshTokenRepository.save(refreshToken);
		return refreshToken;
	}

	@Override
	public boolean VerifyExpiration(RefreshToken token) throws Exception {
		if(token.getExpireDate().isBefore(Instant.now())) {
		
			refreshTokenRepository.delete(token);
			throw new Exception(token.getToken()+ "Refresh token was expired. Please make a new signin request");
			
		}
		
		return true;
	}

	@Override
	public int DeleteByUser(String username	) {
		Employe Ag=agentRepository.findByEmail(username);
		Client customer=customerRepository.findByEmail(username);
		
		if(Ag != null) 
			return refreshTokenRepository.deleteByAgent(Ag.getIdEmploye());
		else if(customer != null)
			return refreshTokenRepository.deleteByCustomer(customer.getIdClient());
		
		return 0;
	}

}
