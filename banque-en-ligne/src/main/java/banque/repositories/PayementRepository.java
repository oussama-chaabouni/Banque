package banque.repositories;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import banque.entities.Payement;

@Transactional
@Repository
public interface PayementRepository extends CrudRepository<Payement, Long>{

	@Modifying
	@Query(value = "INSERT INTO payement(compte_courant_id,beneficiaire, beneficiaire_courant_acc,montant,reference,statut, code_raison,date_operation)" +
			"VALUES(:compte_courant_id,:beneficiaire, :beneficiaire_courant_acc, :montant, :reference, :statut, :code_raison, :date_operation)", nativeQuery= true )
	void ajouterPayement(
			@Param("compte_courant_id") long compte_courant_id,
			@Param("beneficiaire") String beneficiaire,
			@Param("beneficiaire_courant_acc") String beneficiaire_courant_acc,
			@Param("montant") float montant,
			@Param("reference") String reference,
			@Param("statut") String statut,
			@Param("code_raison") String code_raison,
			@Param("date_operation") LocalDateTime date_operation);
	
}
