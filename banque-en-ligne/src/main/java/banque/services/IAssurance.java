package banque.services;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import banque.entities.Assurance;
import banque.entities.Conge;
import banque.entities.Credit;
import banque.entities.TypeAssurance;

public interface IAssurance {
	List<Assurance> retrieveAllAssurances();
	Assurance addAssurance (Assurance a);
    void deleteAssurance (Long id);
	Assurance updateAssurance(Assurance a );
	Assurance retrieveAssuranceById (Long id);
	String suggestionAssurance(Credit c, Assurance a);
	public String suggest(Long id);
	
}
