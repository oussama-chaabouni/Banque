package banque.repositories;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import banque.entities.ScheduledInfoVirementPermanent;

public interface ScheduledInfoVirementPermanentRepo extends CrudRepository<ScheduledInfoVirementPermanent, Long> {
		
	@Query(value = "SELECT MAX(id_transaction) From Scheduled_info_virement_permanent WHERE id_transaction =id_transaction", nativeQuery= true) 
	long getTransactionIdPermanent();
	
	@Query(value = "SELECT transfer_from FROM Scheduled_info_virement_permanent WHERE id_transaction =:id_transaction ", nativeQuery= true) 
	long getTransferFrom(@Param("id_transaction") long id_transaction );
	
	@Query(value = "SELECT transfer_to FROM Scheduled_info_virement_permanent WHERE id_transaction =:id_transaction ", nativeQuery= true) 
	long getTransferTo(@Param("id_transaction") long id_transaction );
	
	@Query(value = "SELECT montant FROM Scheduled_info_virement_permanent WHERE id_transaction =:id_transaction ", nativeQuery= true) 
	float getMontant(@Param("id_transaction") long id_transaction );
	
	@Query(value = "SELECT motif FROM Scheduled_info_virement_permanent WHERE id_transaction =:id_transaction ", nativeQuery= true) 
	String getMotif(@Param("id_transaction") long id_transaction );
	
	@Query(value = "SELECT duree FROM Scheduled_info_virement_permanent WHERE id_transaction =:id_transaction ", nativeQuery= true) 
	int getDuree(@Param("id_transaction") long id_transaction );
	
	@Query(value = "SELECT periode FROM Scheduled_info_virement_permanent WHERE id_transaction =:id_transaction ", nativeQuery= true) 
	int getPeriode(@Param("id_transaction") long id_transaction );

	
	

}
