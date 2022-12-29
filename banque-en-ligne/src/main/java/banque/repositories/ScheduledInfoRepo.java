package banque.repositories;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import banque.entities.ScheduledInfo;

public interface ScheduledInfoRepo extends CrudRepository<ScheduledInfo, Long> {
		
	@Query(value = "SELECT MAX(id_transaction) From Scheduled_info WHERE id_transaction =id_transaction", nativeQuery= true) 
	long getTransactionIdDiffere();
	
	@Query(value = "SELECT transfer_from FROM Scheduled_info WHERE id_transaction =:id_transaction ", nativeQuery= true) 
	String getTransferFrom(@Param("id_transaction") long id_transaction );
	
	@Query(value = "SELECT transfer_to FROM Scheduled_info WHERE id_transaction =:id_transaction ", nativeQuery= true) 
	String getTransferTo(@Param("id_transaction") long id_transaction );
	
	@Query(value = "SELECT montant FROM Scheduled_info WHERE id_transaction =:id_transaction ", nativeQuery= true) 
	float getMontant(@Param("id_transaction") long id_transaction );
	
	@Query(value = "SELECT motif FROM Scheduled_info WHERE id_transaction =:id_transaction ", nativeQuery= true) 
	String getMotif(@Param("id_transaction") long id_transaction );
	
	

}
