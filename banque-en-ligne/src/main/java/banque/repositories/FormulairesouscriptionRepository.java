package banque.repositories;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.transaction.annotation.Transactional;

import banque.entities.Formulairesouscription;
import banque.entities.Titredecivilite;

@Transactional
@Repository
public interface FormulairesouscriptionRepository extends CrudRepository<Formulairesouscription, Long>{
	
	@Modifying
	@Query(value = "INSERT INTO formulairesouscription(titredecivilite,nom,prenom,datenaissance,numero,email, titredecivilitetuteur,nomtuteur,prenomtuteur,datenaissancetuteur,numerotuteur,emailtuteur,pieceditentite,justificatifdedomicile,depotinitial)" +
			"VALUES(:titredecivilite,:nom,:prenom,:datenaissance,:numero,:email, :titredecivilitetuteur,:nomtuteur,:prenomtuteur,:datenaissancetuteur,:numerotuteur,:emailtuteur,:pieceditentite,:justificatifdedomicile,:depotinitial)", nativeQuery= true )
	void ajouterFormulairesouscription(
			
			@Param("titredecivilite") String titredecivilite,
			@Param("nom") String nom,
			@Param("prenom") String prenom,
			@Param("datenaissance") Date datenaissance,
			@Param("numero") long numero,
			@Param("email") String email,
			
			@Param("titredecivilitetuteur") String titredecivilitetuteur,
			@Param("nomtuteur") String nomtuteur,
			@Param("prenomtuteur") String prenomtuteur,
			@Param("datenaissancetuteur") String datenaissancetuteur,
			@Param("numerotuteur") String numerotuteur,
			@Param("emailtuteur") String emailtuteur,
			
			
			@Param("pieceditentite") String pieceditentite,
			@Param("justificatifdedomicile") String justificatifdedomicile,
			@Param("depotinitial") long depotinitial
			
			);
	


	
}	