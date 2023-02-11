package banque.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import banque.entities.CompteEpargne;
import banque.repositories.CompteEpargneRepository;

@Service
public class CompteEpargneServiceImpl implements CompteEpargneService{
	
	@Autowired
	CompteEpargneRepository CompteEpargneRep;
	
	@Override
	public List<CompteEpargne> retrieveAllCompteEpargnes() {
		List<CompteEpargne> compteepargnes= (List<CompteEpargne>) (CompteEpargneRep.findAll());
		//traja3ali de type iterable , donc nrodha traja3 type compteepargne
		return compteepargnes;
	}


	
	@Override
	public CompteEpargne retrieveCompteEpargne(Long id) {
		return CompteEpargneRep.findById(id).get();
	}

	@Override
	public CompteEpargne addCompteEpargne(CompteEpargne t) {
		return CompteEpargneRep.save(t);
	}
	

	@Override
	public void deleteCompteEpargne(Long id) {
		CompteEpargneRep.deleteById(id);

	}

	@Override
	public CompteEpargne updateCompteEpargne(CompteEpargne t) {
		return CompteEpargneRep.save(t);
	}

}