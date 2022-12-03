package banque.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import banque.entities.Employe;
import banque.entities.Salaire;
import banque.repositories.EmployeeRepository;
import banque.repositories.SalaireRepository;
import lombok.SneakyThrows;

@Service
public class SalaireService {
	
	
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
			salaireNet=s.getSalaire()+s.getPrixHeureSup()*s.getNbHeureSup()-s.getTotalTax();
			s.setSalaireNet(salaireNet);
			System.out.println("salaire Employee "+ e.getNom() +" est égal : "+salaireNet);
			SR.save(s);  
	}}
}
