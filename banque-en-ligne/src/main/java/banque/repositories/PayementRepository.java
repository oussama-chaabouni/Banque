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
	@Query(value = "INSERT INTO payement(rib,beneficiaire, beneficiaire_rib,montant,motif,statut, code_raison,date_operation)" +
			"VALUES(:rib,:beneficiaire, :beneficiaire_rib, :montant, :motif, :statut, :code_raison, :date_operation)", nativeQuery= true )
	void ajouterPayement(
			@Param("rib") String rib,
			@Param("beneficiaire") String beneficiaire,
			@Param("beneficiaire_rib") String beneficiaire_rib,
			@Param("montant") float montant,
			@Param("motif") String motif,
			@Param("statut") String statut,
			@Param("code_raison") String code_raison,
			@Param("date_operation") LocalDateTime date_operation);
	
}
