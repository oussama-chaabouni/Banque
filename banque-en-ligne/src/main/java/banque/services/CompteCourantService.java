package banque.services;

import java.util.List;

import banque.entities.CompteCourant;

public interface CompteCourantService {
	
	List<CompteCourant> retrieveAllCompteCourants();
	CompteCourant retrieveCompteCourant(Long id);
	CompteCourant addCompteCourant(CompteCourant t);
	void deleteCompteCourant(Long id);
	CompteCourant updateCompteCourant(CompteCourant t);

}