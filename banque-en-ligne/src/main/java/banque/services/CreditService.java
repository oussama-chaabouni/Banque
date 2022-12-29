package banque.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import banque.entities.Credit;
import banque.repositories.CreditRepo;


@Service
public class CreditService implements ICreditService{
private static final String String = null;
@Autowired
CreditRepo cr;
	@Override
	public List<Credit> retrieveAllCredits() {
		return (List<Credit>) cr.findAll();
	}
	




	@Override
	public String getDescById(Long idCredit) {
		Credit creditt = cr.findById(idCredit).orElse(null);
	String desc=creditt.getDescriptionCredit();
		return desc;
		
	}



	
	

}
