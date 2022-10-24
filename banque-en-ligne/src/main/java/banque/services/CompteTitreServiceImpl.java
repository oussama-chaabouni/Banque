package banque.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import banque.entities.CompteTitre;
import banque.repositories.CompteTitreRepository;

@Service
public class CompteTitreServiceImpl implements ICompteTitreService {
	@Autowired
	CompteTitreRepository CompteTitreRepository;

	@Override
	public CompteTitre addCompteTitre(CompteTitre t, Long IdC) {
		
		//SavingsAccount savingsAccount = new SavingsAccount();
		
		
		return null;
	}
	
	
	
	
	
	
}
