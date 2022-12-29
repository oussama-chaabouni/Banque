package banque.services;

import java.util.List;

import banque.entities.Payement;

public interface PayementService {
	
	List<Payement> retrieveAllPayements();
	Payement retrievePayement(Long id);
	Payement addPayement(Payement p);
	void deletePayement(Long id);
	Payement updatePayement(Payement p);

}
