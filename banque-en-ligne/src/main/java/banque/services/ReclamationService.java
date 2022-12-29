package banque.services;

import java.time.LocalDateTime;
import java.util.List;

import banque.entities.Reclamation;
import banque.entities.TypeTransaction;

public interface ReclamationService {
	
	List<Reclamation> retrieveAllReclamations();
	Reclamation retrieveReclamation(Long id);
	Reclamation addReclamation(Reclamation r);
	void deleteReclamation(Long id);
	Reclamation updateReclamation(Reclamation r);
	
	void ajouterRec(String rib,String typeTransaction ,float montant,String motif,String statut, String raison,LocalDateTime creeLe);

	

}
