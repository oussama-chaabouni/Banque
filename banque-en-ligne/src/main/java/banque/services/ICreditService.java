package banque.services;

import java.util.List;

import banque.entities.Credit;


public interface ICreditService {
	List<Credit> retrieveAllCredits();

	

	String getDescById(Long idCredit);
}
