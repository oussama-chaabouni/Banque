package banque.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import banque.entities.Client;
import banque.entities.ImageUtil;
import banque.repositories.ClientRepository;

@Service
public class ClientServiceImpl implements ICustomerService {
	@Autowired
	ClientRepository customerRep;
	@Override
	public Client getCustomerByemail(String mail) {
		return customerRep.findByEmail(mail);
	}
	@Override
	public Client addClient(Client a,MultipartFile image) throws UnsupportedEncodingException, MessagingException {
		PasswordEncoder encoder=new BCryptPasswordEncoder();
		a.setPassword(encoder.encode(a.getPassword()));
		
		System.out.println(image+"******************");
		try {
			a.setImageData(ImageUtil.compressImage(image.getBytes()));
			a.setStatus(false);
			a.setEnabled(true);
			customerRep.save(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return a;
	}
	@Override
	public Client addClient2(Client a)  {
		PasswordEncoder encoder=new BCryptPasswordEncoder();
		a.setPassword(encoder.encode(a.getPassword()));
			a.setStatus(false);
			a.setEnabled(true);
			customerRep.save(a);

		return a;
	}
}
