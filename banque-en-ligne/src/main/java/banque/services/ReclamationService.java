package banque.services;

import java.util.List;

import banque.entities.Reclamation;

public interface ReclamationService {
	
	List<Reclamation> retrieveAllReclamations();
	Reclamation retrieveReclamation(Long id);
	Reclamation addReclamation(Reclamation r);
	void deleteReclamation(Long id);
	Reclamation updateReclamation(Reclamation r);

}
