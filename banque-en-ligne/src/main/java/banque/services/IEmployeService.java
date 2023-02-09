package banque.services;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import banque.entities.Employe;

public interface IEmployeService {
	public Employe getEmployeByemail(String mail);
	public Employe addAgent(Employe a) throws UnsupportedEncodingException, MessagingException;
	public Employe addAgent2(Employe a);
}
