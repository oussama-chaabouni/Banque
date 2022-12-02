package banque.services;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import banque.entities.Formation;
import banque.entities.Formation_Details;
import banque.repositories.FormationRepository;
import banque.repositories.Formation_DetailsRepository;





@Service
public class FormationService {

	@Autowired
	FormationRepository FormationRepo;

	@Autowired
	Formation_DetailsRepository FDR;
	


	/* Ajouter une Formation */
	public Formation save(Formation F) {

		return FormationRepo.save(F);

	}

	/* voir tous les Formations */

	public List<Formation> retrieveAllFormations() {
		List<Formation> Formations= (List<Formation>) (FormationRepo.findAll());
		//traja3ali de type iterable , donc nrodha traja3 type transaction
		return Formations;
	}

	/* Delete D"une Formation */
	public void delete(long id) {
		FormationRepo.deleteById(id);
	}

	/* Update d'une Formation */
	public Formation updateFormation(Formation F) {
		return FormationRepo.save(F);

	}

	/* Chercher une Formation */
	/*public Formation findOne(long id_F) {
		return FormationRepo.findById(id_F);
	}*/

	public Formation_Details ParticiperFormation(long idF, String idp) {
		Formation_Details F = new Formation_Details();
		//F.setFormation(findOne(idF));
		//F.setPersonnel(UES.findOne(idp));
		//F.setDate_passage(null);
		F.setEtat("En-cours");
		return FDR.save(F);

	}

	public List<Formation_Details> ParticiperAuformation(long idf) {
		List<Formation_Details> a = FDR.ListePersonnelParticiperAuFormation(idf);

		for (Formation_Details formations : a) {
		

		}
		return a;
	}

	public List<Formation_Details> verficiationInscri(String idp, long idf) {
		List<Formation_Details> a = FDR.verifInscriptionFormation(idp, idf);

		for (Formation_Details formations : a) {
			

		}
		return a;
	}

	public List<Formation> ListeFormationParIdPersno(String idp) {
		List<Formation_Details> a = FDR.ListeFormationParIdPerso(idp);
		List<Formation> mesFormation = new ArrayList();

		for (Formation_Details formations : a) {
			mesFormation.add(formations.getFormation());
		}
		return mesFormation;
	}

	/*public void desinscrireFormation(String idp, long idf) {
		FDR.DesInscriptionFormation(idp, idf);
	}*/

	/*public void updateDateExamen(Date D, String idp, long idf) {
		FDR.updateDateExamen(D, idp, idf);
	}
*/
	/*public List<UserEntity> ListePersonnelNonAffecter(long idf) {

		List<UserEntity> AllUsers = UES.findall();
		List<UserEntity> UsersNonAffecter = new ArrayList();
		List<Formation_Details> UsersAffecter = ParticiperAuformation(idf);
		for (Formation_Details Formation_Details : UsersAffecter) {
			UsersNonAffecter.add(Formation_Details.getPersonnel());
		}
		AllUsers.removeAll(UsersNonAffecter);
		return AllUsers;
	}/*

	// Envoyer Mail au personnel pour informer au date des formations


		*/
}



