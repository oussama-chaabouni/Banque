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
import banque.entities.Salaire;
import banque.repositories.EmployeeRepository;
import banque.repositories.SalaireRepository;
import lombok.SneakyThrows;

public interface SalaireService {
	
	
	List<Salaire> retrieveAllSalaires();
	Salaire retrieveSalaire(Long id);
	Salaire addSalaire(Salaire t);
	void deleteSalaire(Long id);
	Salaire updateSalaire(Salaire t);
}
