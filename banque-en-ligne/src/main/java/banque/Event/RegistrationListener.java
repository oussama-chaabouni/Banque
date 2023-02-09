package banque.Event;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import banque.entities.Client;
import banque.entities.EmailVerificationToken;
import banque.entities.Employe;
import banque.services.IEmailVerificationTokenService;
@Component
public class RegistrationListener implements  ApplicationListener<OnRegistrationCompleteEvent> {
	
	private final String FromAddress="jmlessous.banque.1@gmail.com";
	private final String SenderName="JMLESSOUS";
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private IEmailVerificationTokenService emailVerificationTokenService;
	


	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		try {
			this.confirmRegistration(event);
		} catch (UnsupportedEncodingException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void confirmRegistration(OnRegistrationCompleteEvent event) throws UnsupportedEncodingException, MessagingException {
		String Fullname;
		Employe agent=event.getAgent();
		Client customer=event.getCustomer();
		String recipientAddress;
		 String content = "Cher / Chere [[name]],<br>"
		            + "Veuillez cliquer sur le lien ci-dessous pour vérifier votre inscription:<br>"
		            + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
		            + "Merci,<br>"
		            + "JMLESSOUS";
		 
		if(agent != null) {
			recipientAddress=agent.getEmail();
			 Fullname=agent.getNom()+" "+agent.getPrenom();
			EmailVerificationToken emailVerificationToken=emailVerificationTokenService.createEmailVerificationToken(agent);
			content = content.replace("[[name]]",Fullname);
			String verifyURL = "http://localhost:8082/banque-en-ligne/auth/confirmRegistration/" + emailVerificationToken.getToken();
			content = content.replace("[[URL]]", verifyURL);
			}
		else {
			recipientAddress=customer.getEmail();
			 Fullname=customer.getNom()+" "+customer.getPrenom();
			EmailVerificationToken emailVerificationToken=emailVerificationTokenService.createEmailVerificationToken(customer);
			content = content.replace("[[name]]",Fullname);
			String verifyURL = "http://localhost:8082/banque-en-ligne/auth/confirmRegistration/" + emailVerificationToken.getToken();
			content = content.replace("[[URL]]", verifyURL);
		}
		
		
		MimeMessage message=javaMailSender.createMimeMessage();
		MimeMessageHelper helper =new MimeMessageHelper(message);
		helper.setFrom(FromAddress,SenderName);
		helper.setTo(recipientAddress);
		helper.setSubject("Email Confirmation");
		
		helper.setText(content, true);
	    
	    javaMailSender.send(message);
		
		
		
		
	}
	


}

