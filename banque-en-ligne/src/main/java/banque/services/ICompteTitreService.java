package banque.services;

import org.springframework.stereotype.Service;
import banque.entities.CompteTitre;
import banque.entities.Client;

public interface ICompteTitreService {
	
	public CompteTitre addCompteTitre(CompteTitre t,Long IdC);
	
}
