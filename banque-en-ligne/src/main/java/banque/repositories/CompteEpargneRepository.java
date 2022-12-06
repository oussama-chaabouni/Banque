package banque.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import banque.entities.CompteEpargne;


@Transactional
@Repository
public interface CompteEpargneRepository extends CrudRepository<CompteEpargne, Long>{
	@Query("SELECT Solde FROM CompteEpargne cc WHERE cc.rib= :rib")
	float getSoldeCompteEpargne(@Param("rib") long rib);
	
	@Modifying
	@Query("update CompteEpargne cc set cc.Solde = :new_solde where cc.rib = :rib" )
	void ChangeSoldeCompteEpargneByRib(@Param("new_solde")float new_solde,@Param("rib") long ribc );
	
	

}
