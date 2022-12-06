package banque.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import banque.entities.Employe;
import banque.entities.Salaire;
import banque.entities.Salaire;
import banque.repositories.EmployeeRepository;
import banque.repositories.SalaireRepository;

@Service
public class SalaireServiceImpl implements SalaireService{
	
	@Autowired
	EmployeeRepository EmployeeRepository;
	
	@Autowired
	SalaireRepository SR;
  
	//@Scheduled(fixedDelay = 5000)
	//@Scheduled(cron = "0 30 11 59 * ?", zone = "Europe/Paris")
	public void CalculerSalaireNet() {
		List<Employe> ListDesEmployess = new ArrayList();
		ListDesEmployess = EmployeeRepository.findAll();
		
		for(Employe e : ListDesEmployess) {
						//Empo       //SALAIRE
			float salaireNet = 0;
			Salaire s  = e.getSalaire();
			salaireNet=s.getSalaire()+s.getPrixheuresup()*s.getNbheuresup()-s.getTotaltax();
			s.setSalairenet(salaireNet);
			System.out.println("salaire Employee "+ e.getNom() +" est égal : "+salaireNet);
			SR.save(s);  
	}}

	@Override
	public List<Salaire> retrieveAllSalaires() {
		List<Salaire> transactions= (List<Salaire>) (SR.findAll());
		//traja3ali de type iterable , donc nrodha traja3 type transaction
		return transactions;
	}

	@Override
	public Salaire retrieveSalaire(Long id) {
		return SR.findById(id).get();
	}

	@Override
	public Salaire addSalaire(Salaire t) {
		return SR.save(t);
	}
	

	@Override
	public void deleteSalaire(Long id) {
		SR.deleteById(id);

	}

	@Override
	public Salaire updateSalaire(Salaire t) {
		return SR.save(t);
	}
	
	
	
	
}
