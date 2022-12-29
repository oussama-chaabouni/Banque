package banque.services;

import java.util.List;

import banque.entities.Conge;



public interface ICongeService {
	List<Conge> retrieveAllConges();
    void deleteConge (Long id);
	Conge updateConge(Conge conge );
	Conge retrieveCongeById (Long id);
	List<Conge> getCongeByEmployee(Long idEmploye);
	void acceptConge(Conge conge, Long idConge);
	Conge addConge(Conge c, Long idEmploye);
}
