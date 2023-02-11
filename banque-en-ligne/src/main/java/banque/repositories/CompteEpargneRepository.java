package banque.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import banque.entities.CompteEpargne;


@Transactional
@Repository
public interface CompteEpargneRepository extends CrudRepository<CompteEpargne, Long>{
	@Query("SELECT Solde FROM CompteEpargne cc WHERE cc.rib= :rib")
	float getSoldeCompteEpargne(@Param("rib") String rib);
	
	@Modifying
	@Query("update CompteEpargne cc set cc.Solde = :new_solde where cc.rib = :rib" )
	void ChangeSoldeCompteEpargneByRib(@Param("new_solde")float new_solde,@Param("rib") String ribc );
	
	@Query(value = "SELECT nom FROM compte_epargne  WHERE rib =:rib ", nativeQuery= true)
	String NombyRib(@Param("rib") String rib);

	@Modifying
	@Query(value = "INSERT INTO compte_epargne(rib,ibane,plafond,solde, interet, type_epargne,nom)" +
			"VALUES(:rib,:ibane,:plafond,:solde, :interet, :type_epargne, :nom)", nativeQuery= true )
	void ajouterCompteEpargne(

			@Param("rib") String rib,
			@Param("ibane") String ibane,
			@Param("plafond") int plafond,
			@Param("solde") float solde,
			@Param("interet") float interet,
			@Param("type_epargne") String type_epargne,
			@Param("nom") String nom
			);
}
