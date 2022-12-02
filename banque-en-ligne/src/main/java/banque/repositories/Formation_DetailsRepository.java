package banque.repositories;



import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import banque.entities.Formation_Details;



@Repository
public interface Formation_DetailsRepository extends JpaRepository<Formation_Details, Long> {
	@Query(value = "SELECT * FROM formation_details where idp=?1 ", nativeQuery = true)
	List<Formation_Details> ListeFormationParIdPerso(String IdP);
	@Query(value = "SELECT * FROM formation_details where idf=?1 ", nativeQuery = true)
	List<Formation_Details> ListePersonnelParticiperAuFormation(long IdP);
	@Query(value = "SELECT * FROM formation_details where idp =?1 and idf=?2 ", nativeQuery = true)
	List<Formation_Details> verifInscriptionFormation(String idP,long idf);
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "DELETE FROM formation_details WHERE idp=?1 and idf=?2 ", nativeQuery = true)
	public void DesInscriptionFormation(String idP,long idf);
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE formation_details SET date_passage=?1 WHERE idp=?2 and idf=?3", nativeQuery = true)
	public void updateDateExamen(Date Date_passage ,String idP,long idf);
	
	

	
	
	
}

