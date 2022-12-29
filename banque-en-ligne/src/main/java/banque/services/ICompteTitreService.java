package banque.services;

import java.util.List;

import org.springframework.stereotype.Service;
import banque.entities.CompteTitre;
import banque.entities.Action;
import banque.entities.Client;

public interface ICompteTitreService {
	
	public CompteTitre addCompteTitre(CompteTitre t,Long IdC);
	List<CompteTitre> retrieveAllCompteTitres();
	CompteTitre retrieveCompteTitre(long id);
}
