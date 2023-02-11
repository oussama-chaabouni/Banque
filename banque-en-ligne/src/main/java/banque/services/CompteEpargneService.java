package banque.services;

import java.util.List;

import banque.entities.CompteEpargne;

public interface CompteEpargneService {
	
	List<CompteEpargne> retrieveAllCompteEpargnes();
	CompteEpargne retrieveCompteEpargne(Long id);
	CompteEpargne addCompteEpargne(CompteEpargne t);
	void deleteCompteEpargne(Long id);
	CompteEpargne updateCompteEpargne(CompteEpargne t);

}
