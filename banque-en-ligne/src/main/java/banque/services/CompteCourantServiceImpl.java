package banque.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import banque.entities.CompteCourant;
import banque.repositories.CompteCourantRepository;

@Service
public class CompteCourantServiceImpl implements CompteCourantService{
	
	@Autowired
	CompteCourantRepository CompteCourantRep;
	
	@Override
	public List<CompteCourant> retrieveAllCompteCourants() {
		List<CompteCourant> comptecourants= (List<CompteCourant>) (CompteCourantRep.findAll());
		//traja3ali de type iterable , donc nrodha traja3 type comptecourant
		return comptecourants;
	}


	
	@Override
	public CompteCourant retrieveCompteCourant(Long id) {
		return CompteCourantRep.findById(id).get();
	}

	@Override
	public CompteCourant addCompteCourant(CompteCourant t) {
		return CompteCourantRep.save(t);
	}
	

	@Override
	public void deleteCompteCourant(Long id) {
		CompteCourantRep.deleteById(id);

	}

	@Override
	public CompteCourant updateCompteCourant(CompteCourant t) {
		return CompteCourantRep.save(t);
	}

}