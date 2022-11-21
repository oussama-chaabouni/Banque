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
	@Query("update Transaction t set t.typeTransaction = :typetr where t.rib = :rib")
	int updateTypeTransactionBycompteCourantRib(@Param("typetr") TypeTransaction typeTransaction , @Param("rib") long rib);
	
	@Modifying
	@Query("DELETE FROM Transaction t WHERE t.typeTransaction= :typeTr AND t.rib = :rib")
	int deleteTransactionByTypeTransactionAndRibc(@Param("typeTr") TypeTransaction typeTransaction, @Param("rib") long rib);
	
	
	@Modifying
	@Query(value = "INSERT INTO transaction(rib,type_transaction,montant,motif,statut, code_raison,date_operation,solde)" +
			"VALUES(:rib,:type_transaction,:montant,:motif,:statut, :code_raison,:date_operation,:solde)", nativeQuery= true )
	void ajouterTransaction(
			@Param("rib") long rib,
			@Param("type_transaction") String type_transaction,
			@Param("montant") float montant,
			@Param("motif") String motif,
			
			@Param("statut") String statut,
			@Param("code_raison") String code_raison,
			@Param("date_operation") LocalDateTime date_operation,
			@Param("solde")float solde);

/*	
	//7abit nesta3malha fel onetoone bech nrecuperi el transactionid mel Transaction
	@Query("SELECT idTransaction FROM Transaction t WHERE t.compteCourantId= :compteCourantId AND t.typeTransaction=:typeTransaction AND t.montant=:montant "+
	"AND t.Ribc=:ribc AND t.statut=:statut AND t.codeRaison=:codeRaison AND t.dateOperation=:dateOperation AND Solde=:solde")
	long findTopByOrderByTransactionIdDesc(@Param("compteCourantId") long compteCourantId,
			@Param("typeTransaction") TypeTransaction typeTransaction,
			@Param("montant") float montant,
			@Param("ribc") String ribc,
			@Param("statut") String statut,
			@Param("codeRaison") String codeRaison,
			@Param("dateOperation") LocalDateTime dateOperation,
			@Param("solde")float solde);
*/	
	
	
	//value = "SELECT transaction_id FROM Transaction WHERE created_at = :created_at", nativeQuery = true
	@Query(value = "SELECT MAX(id_transaction) FROM Transaction  WHERE rib =:rib ", nativeQuery= true) 
	long findTopByOrderByIdTransactionDesc(@Param("rib") long rib );
	
	@Query("SELECT dateOperation FROM Transaction t WHERE t.idTransaction =:idTransaction") 
	LocalDateTime getDateDebutDuDecouvert(@Param("idTransaction") long idTransaction );
	
	
	//@Query(value = "SELECT transaction_id FROM Transaction WHERE created_at = :created_at", nativeQuery = true)
	//Long findTopByOrderByTransactionIdDesc(@Param("created_at") LocalDateTime created_at);
	

	
	
}
