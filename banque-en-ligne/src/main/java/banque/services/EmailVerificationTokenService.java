package banque.services;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import banque.entities.Client;
import banque.entities.EmailVerificationToken;
import banque.entities.Employe;
import banque.repositories.ClientRepository;
import banque.repositories.EmailVerificationTokenRepository;
import banque.repositories.EmployeRepo;
import banque.Exception.TokenEmailException;
@Service
public class EmailVerificationTokenService implements IEmailVerificationTokenService {
	
	@Value("${email.emailTokenDurationMs}")
	private Long emailTokenDurationMs;
	@Autowired
	EmailVerificationTokenRepository EMVRep;
	@Autowired
	EmployeRepo employeRepository;
	@Autowired
	ClientRepository customerRepository;
	

	@Override
	public EmailVerificationToken createEmailVerificationToken(Object user) {
		EmailVerificationToken EMV=new EmailVerificationToken();
		EMV.setToken(UUID.randomUUID().toString());
		EMV.setExpireDate(Instant.now().plusSeconds(emailTokenDurationMs));
		if(user instanceof Employe)
			EMV.setEmployeEmail(employeRepository.findByEmail(((Employe) user).getEmail()));
		else if(user instanceof Client)
			EMV.setCustomerEmail(customerRepository.findByEmail(((Client) user).getEmail()));

		EMVRep.save(EMV);
		return EMV;
	}

	
	private boolean VerifyExpiration(EmailVerificationToken token) {
		if(token.getExpireDate().isBefore(Instant.now())) {
			EMVRep.delete(token);
			throw new TokenEmailException(token.getToken(), "this token was expired");
		}
		
		return true;
	}

	@Override
	public void ConfirmUserRegistration(String Token) {
		EmailVerificationToken emailVerificationToken=EMVRep.findByToken(Token);
		Employe employe=emailVerificationToken.getEmployeEmail();
		Client customer=emailVerificationToken.getCustomerEmail();

		this.VerifyExpiration(emailVerificationToken);
		if(employe != null) {
			
				
			employe.setStatus(true);
			employeRepository.save(employe);
		}
		else if(customer != null) {
			
				customer.setStatus(true);
				customerRepository.save(customer);
		}

			
	}


	@Override
	public List<EmailVerificationToken> getExpireToken() {
		
		return EMVRep.findExpireToken(Instant.now());
	}


	@Override
	public void deleteToken(Long id) {
		EMVRep.deleteById(id);
		
	}

}
