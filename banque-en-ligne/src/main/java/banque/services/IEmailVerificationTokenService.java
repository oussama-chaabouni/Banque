package banque.services;
import java.util.Date;
import java.util.List;

import banque.entities.EmailVerificationToken;
public interface IEmailVerificationTokenService {
	EmailVerificationToken createEmailVerificationToken(Object user);
	
	void ConfirmUserRegistration(String Token);
	
	List<EmailVerificationToken> getExpireToken();
	
	void deleteToken(Long id);

}
