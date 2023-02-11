package banque.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.web.multipart.MultipartFile;

import banque.entities.Client;

public interface ICustomerService {
	public Client getCustomerByemail(String mail);
	public Client addClient(Client a,MultipartFile image) throws UnsupportedEncodingException, MessagingException;
	public Client addClient2(Client a);
	public Client addImage(Long id,MultipartFile image) throws IOException;
}
