package banque.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import banque.entities.Transaction;
import banque.entities.TypeTransaction;
import banque.repositories.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	TransactionRepository TransactionRep;


	@Override
	public List<Transaction> retrieveAllTransactions() {
		List<Transaction> transactions= (List<Transaction>) (TransactionRep.findAll());
		//traja3ali de type iterable , donc nrodha traja3 type transaction
		return transactions;
	}

	@Override
	public Transaction retrieveTransaction(Long id) {
		return TransactionRep.findById(id).get();
	}

	@Override
	public Transaction addTransaction(Transaction t) {
		return TransactionRep.save(t);
	}
	

	@Override
	public void deleteTransaction(Long id) {
		TransactionRep.deleteById(id);

	}

	@Override
	public Transaction updateTransaction(Transaction t) {
		return TransactionRep.save(t);
	}
	
//JPQL
	@Override
	public List<Transaction> retrieveTransactsByTypeTransaction(TypeTransaction typeTransaction) {
		return TransactionRep.retrieveTransactionsByTypeTransaction(typeTransaction);		
	}

	@Override
	public int updateTypeTransactBycompteCourantId(TypeTransaction typeTransaction, long compteCourantId) {
		return TransactionRep.updateTypeTransactionBycompteCourantId(typeTransaction,compteCourantId);
	}

	@Override
	public int deleteTransactByTypeTransactionAndSource(TypeTransaction typeTransaction, String source) {
		return TransactionRep.deleteTransactionByTypeTransactionAndSource(typeTransaction, source);
	}

	@Override
	public void insertTransact(long compteCourantId,TypeTransaction typeTransaction, float montant, String source, String statut,String raison, LocalDateTime creeLe) {
		TransactionRep.insertTransaction(compteCourantId,typeTransaction, montant, source, statut, raison, creeLe);
		
	}

	
		
	
	
	
}
