package banque.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import banque.entities.CompteCourant;

@Transactional
@Repository
public interface CompteCourantRepository extends JpaRepository<CompteCourant, Long> {
	
	
	@Query("SELECT Solde FROM CompteCourant cc WHERE cc.rib= :rib")
	float getSoldeCompteCourant(@Param("rib") long rib);
	
	@Modifying
	@Query("update CompteCourant cc set cc.Solde = :new_solde where cc.rib = :rib" )
	void ChangeSoldeCompteCourantByRib(@Param("new_solde")float new_solde,@Param("rib") long ribc );
	
	
	@Query(value = "SELECT nom_client FROM compte_courant  WHERE rib =:rib ", nativeQuery= true)
	String NombyRib(@Param("rib") long rib);
	

}
