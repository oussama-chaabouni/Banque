package banque.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import banque.entities.Transaction;
import banque.entities.TypeTransaction;

public interface TransactionService {

	List<Transaction> retrieveAllTransactions();
	Transaction retrieveTransaction(Long id);
	Transaction addTransaction(Transaction t);
	void deleteTransaction(Long id);
	Transaction updateTransaction(Transaction t);
	
	//JPQL
	List<Transaction> retrieveTransactsByTypeTransaction(TypeTransaction typeTransaction);
	int updateTypeTransactBycompteCourantId(TypeTransaction typeTransaction ,long compteCourantId);
	int deleteTransactByTypeTransactionAndSource(TypeTransaction typeTransaction,String source);
	void insertTransact(long compteCourantId,TypeTransaction typeTransaction ,float montant, String source,String statut, String raison,LocalDateTime creeLe);
	
}
