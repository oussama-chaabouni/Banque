package banque.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import banque.entities.Action;
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

	@Override
	public List<CompteTitre> retrieveAllCompteTitres() {
		List<CompteTitre> c = (List<CompteTitre>) CompteTitreRepository.findAll();
		return c;
	}
	
	@Override
	public CompteTitre retrieveCompteTitre(long id) {
		return CompteTitreRepository.findById(id).orElse(null);
	}
	
	
	
	
	
}
