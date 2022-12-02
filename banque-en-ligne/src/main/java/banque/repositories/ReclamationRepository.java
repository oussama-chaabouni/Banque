package banque.repositories;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import banque.entities.Reclamation;

@Transactional
@Repository
public interface ReclamationRepository extends CrudRepository<Reclamation, Long>{
	
	@Modifying
	@Query(value = "INSERT INTO reclamation(rib,type_transaction,montant,motif,statut, code_raison,date_operation)" +
			"VALUES(:rib,:type_transaction,:montant,:motif,:statut,:code_raison,:date_operation)", nativeQuery= true )
	void ajouterReclamation(
			@Param("rib") long rib,
			@Param("type_transaction") String type_transaction,
			@Param("montant") float montant,
			@Param("motif") String motif,
			@Param("statut") String statut,
			@Param("code_raison") String code_raison,
			@Param("date_operation") LocalDateTime date_operation); 

}
