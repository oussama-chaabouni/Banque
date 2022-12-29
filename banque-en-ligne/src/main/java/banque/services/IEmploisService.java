package banque.services;

import java.util.List;

import banque.entities.Conge;
import banque.entities.Emplois;

public interface IEmploisService {

	List<Emplois> retrieveAllEmplois();
	Emplois addEmplois(Emplois e);
	 void deleteEmplois(Long id);
	 Emplois updateEmplois(Emplois e) ;
	 Emplois retrieveEmploisById(Long id) ;

}
