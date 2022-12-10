package banque.services;

import java.util.List;
import java.util.Map;

import banque.entities.Credit;

public interface ICreditService {
	
	
	
	List<Credit> retrieveAllCredits();

	Credit addCredit (Credit c);

	Credit updateCredit (Credit c);

	Credit retrieveCredit (Long id);

	void removeCredit (Long id);

	Map<Object, Object> getScoreAttr(Long loanId);

	Map<Object, Object> scoreDataTransform(String sex, String housing, double checkingAccount, String purpose);

	double predictScore(long id);

	Credit addLoanRequest(Credit l, Long IdC);
	
	Credit acceptLoanRequest(Long id);
	
	Credit denyLoanRequest(Long id);

}
