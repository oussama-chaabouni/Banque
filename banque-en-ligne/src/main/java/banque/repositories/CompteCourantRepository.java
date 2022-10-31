package banque.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import banque.entities.CompteCourant;

@Transactional
@Repository
public interface CompteCourantRepository extends CrudRepository<CompteCourant, Long> {
	
	
	@Query("SELECT Solde FROM CompteCourant cc WHERE cc.idCompteCourant= :idCompteCourant")
	float getSoldeCompteCourant(@Param("idCompteCourant") long idCompteCourant);
	
	@Modifying
	@Query("update CompteCourant cc set cc.Solde = :new_solde where cc.idCompteCourant = :idCompteCourant" )
	void ChangeSoldeCompteCourantById(@Param("new_solde")float new_solde,@Param("idCompteCourant") long idCompteCourant );
	

}
