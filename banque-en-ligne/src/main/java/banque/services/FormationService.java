package banque.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import banque.entities.Formation;
import banque.entities.Formation_Details;
import banque.repositories.EmployeeRepository;
import banque.repositories.FormationDetailsRepository;
import banque.repositories.FormationDetailsRepository;
import banque.repositories.FormationRepository;

@Service
public class FormationService {
	@Autowired
	FormationRepository FormationRepo;
	
	@Autowired
	FormationDetailsRepository FormationDetailsRepo;
	
	@Autowired
	EmployeeRepository EmployeeRepository;
	
	@Autowired
	FormationDetailsRepository FDR;
	/* voir tous les Formations */
	public List<Formation> findall() {
		List<Formation> a = FormationRepo.findAll();

		for (Formation formations : a) {
			//L.info("Formation de détails  :" + formations);

		}
		return a;
	}
	
	//findallMyFormation
		public List<Formation> ListeFormationParIdEmploye(long idp) {
			List<Formation_Details> a = FDR.ListeFormationParIdEmploye(idp);
			List<Formation> mesFormation = new ArrayList();

			for (Formation_Details formations : a) {
				mesFormation.add(formations.getFormation());
			}
			return mesFormation;
		}
		
	/* voir tous les Formations Details */
	public List<Formation_Details> findallDetails() {
		List<Formation_Details> a = FormationDetailsRepo.findAll();

		for (Formation_Details formations_details : a) {
			//L.info("Formation de détails  :" + formations);

		}
		return a;
	}
	
	//afficher emp participants
	public List<Formation_Details> EmpParticipants(long idf) {
		List<Formation_Details> a = FDR.ListeEmployesParticiperAuFormation(idf);

		for (Formation_Details formations : a) {
		//	L.info("Formation de détails  :" + formations);

		}
		return a;
	}
	
	/* Delete D"une Formation */
	public void delete(long id) {
		FormationRepo.deleteById(id);
	}
	/* Ajouter une Formation */
	public Formation save(Formation F) {

		return FormationRepo.save(F);

	}
	/* Chercher une Formation */
	public Formation findOne(long id_F) {
		return FormationRepo.findById(id_F).get();
	}

	
	public Formation_Details ParticiperFormation(long idF, long idp) {
		Formation_Details F = new Formation_Details();
		F.setFormation(findOne(idF));
		F.setEmploye(EmployeeRepository.findById(idp).get());
		F.setEtat("En-cours");
		return FDR.save(F);

	}
	

	
	public void desinscrireFormation(long idp, long idf) {
		FDR.DesInscriptionFormation(idp, idf);
	}
	
}
