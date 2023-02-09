package banque.services;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import banque.entities.Client;
import banque.entities.Employe;
import banque.repositories.EmployeRepo;

@Service
public class EmployeServiceImpl implements IEmployeService {
	@Autowired
	EmployeRepo employeRep;
	@Override
	public Employe getEmployeByemail(String Email) {
		return employeRep.findByEmail(Email);
	}
	@Override
	public Employe addAgent(Employe a) throws UnsupportedEncodingException, MessagingException {
		PasswordEncoder encoder=new BCryptPasswordEncoder();
		a.setPassword(encoder.encode(a.getPassword()));
		a.setEnabled(true);
		a.setStatus(false);
		employeRep.save(a);
		return a;
	}
	
	@Override
	public Employe addAgent2(Employe a)  {
		PasswordEncoder encoder=new BCryptPasswordEncoder();
		a.setPassword(encoder.encode(a.getPassword()));
			a.setStatus(false);
			a.setEnabled(true);
			employeRep.save(a);

		return a;
	}

}
