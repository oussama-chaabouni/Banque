package banque.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import banque.entities.Reclamation;
import banque.repositories.ReclamationRepository;

@Service
public class ReclamationServiceImpl implements ReclamationService{

	@Autowired
	ReclamationRepository ReclamationRep;
	
	@Override
	public List<Reclamation> retrieveAllReclamations() {
		List<Reclamation> reclamations= (List<Reclamation>) (ReclamationRep.findAll());
		//traja3ali de type iterable , donc nrodha traja3 type reclamation
		return reclamations;
	}

	@Override
	public Reclamation retrieveReclamation(Long id) {
		return ReclamationRep.findById(id).get();
	}

	@Override
	public Reclamation addReclamation(Reclamation r) {
		return ReclamationRep.save(r);
	}
	

	@Override
	public void deleteReclamation(Long id) {
		ReclamationRep.deleteById(id);

	}

	@Override
	public Reclamation updateReclamation(Reclamation r) {
		return ReclamationRep.save(r);
	}

}
