package banque.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import banque.entities.Formation_Details;

@Transactional
@Repository
public interface FormationDetailsRepository extends JpaRepository<Formation_Details, Long>{
	@Query(value = "SELECT * FROM formation_details where id_employe=?1 ", nativeQuery = true)
	List<Formation_Details> ListeFormationParIdEmploye(long IdP);
	
	@Query(value = "SELECT * FROM formation_details where id_employe =?1 and idf=?2 ", nativeQuery = true)
	List<Formation_Details> verifInscriptionFormation(long idP,long idf);
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "DELETE FROM formation_details WHERE id_employe=?1 and idf=?2 ", nativeQuery = true)
	public void DesInscriptionFormation(long idP,long idf);
	
	@Query(value = "SELECT * FROM formation_details where idf=?1 ", nativeQuery = true)
	List<Formation_Details> ListePersonnelParticiperAuFormation(long IdP);

	
	
}
