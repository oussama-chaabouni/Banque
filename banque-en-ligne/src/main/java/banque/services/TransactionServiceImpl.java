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
	public int updateTypeTransactBycompteCourantRib(TypeTransaction typeTransaction, long rib) {
		return TransactionRep.updateTypeTransactionBycompteCourantRib(typeTransaction,rib);
	}

	@Override
	public int deleteTransactByTypeTransactionAndRibc(TypeTransaction typeTransaction, long rib) {
		return TransactionRep.deleteTransactionByTypeTransactionAndRibc(typeTransaction, rib);
	}



	
		
	
	
	
}
