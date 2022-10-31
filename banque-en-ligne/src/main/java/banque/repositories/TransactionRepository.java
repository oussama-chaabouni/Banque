package banque.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import banque.entities.Transaction;
import banque.entities.TypeTransaction;

@Transactional
@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long>{
	
	//li ba3d el :ena nsammh kima n7eb ama nhot nafsou fel @param()
	//JPQL
	@Query("SELECT t FROM Transaction t WHERE t.typeTransaction= :typeTransaction")
	List<Transaction> retrieveTransactionsByTypeTransaction(@Param("typeTransaction") TypeTransaction typeTransaction);
	
	@Modifying
	@Query("update Transaction t set t.typeTransaction = :typetr where t.compteCourantId = :compteCourantId")
	int updateTypeTransactionBycompteCourantId(@Param("typetr") TypeTransaction typeTransaction , @Param("compteCourantId") long compteCourantId);
	
	@Modifying
	@Query("DELETE FROM Transaction t WHERE t.typeTransaction= :typeTr AND t.source = :source")
	int deleteTransactionByTypeTransactionAndSource(@Param("typeTr") TypeTransaction typeTransaction, @Param("source") String source);
	
	@Modifying
	@Query(value = "INSERT INTO Transaction (compteCourantId, typeTransaction, montant,source,statut,raison,creeLe) VALUES (:compteCourantId, :typeTr, :montant,:source,:statut,:raison,:creele)",nativeQuery = true)
	void insertTransaction(@Param("compteCourantId") long compteCourantId ,@Param("typeTr") TypeTransaction typeTransaction ,@Param("montant") float montant, @Param("source") String source, @Param("statut") String statut, @Param("raison") String raison,@Param("creele") LocalDateTime creeLe);
	
	@Modifying
	@Query(value = "INSERT INTO transaction(compte_courant_id,type_transaction,montant,source,statut, code_raison,date_operation)" +
			"VALUES(:compte_courant_id,:type_transaction,:montant,:source,:statut, :code_raison,:date_operation)", nativeQuery= true )
	void ajouterTransaction(
			@Param("compte_courant_id") long compte_courant_id,
			@Param("type_transaction") String type_transaction,
			@Param("montant") float montant,
			@Param("source") String source,
			@Param("statut") String statut,
			@Param("code_raison") String code_raison,
			@Param("date_operation") LocalDateTime date_operation);
	

	
}
