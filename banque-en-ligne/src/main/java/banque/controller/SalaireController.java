package banque.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import banque.entities.Salaire;
import banque.repositories.CompteCourantRepository;
import banque.repositories.PayementRepository;
import banque.repositories.SalaireRepository;
import banque.services.GlobalRestService;
import banque.services.GlobalRestServiceVirementPermanent;
import banque.services.ReclamationService;
import banque.services.SalaireService;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/salaire")
public class SalaireController {
	
	@Autowired
	SalaireService salaireService;
	
	
	// http://localhost:8082/banque-en-ligne/salaire/retrieve-all-salaires
	@GetMapping("/retrieve-all-salaires")
	@ResponseBody
	public List<Salaire> getSalaires() {
	List<Salaire> listSalaires = salaireService.retrieveAllSalaires();
	return listSalaires;
	}
	
	// http://localhost:8082/banque-en-ligne/salaire/retrieve-salaire/8
	@GetMapping("/retrieve-salaire/{salaire-id}")
	@ResponseBody
	public Salaire retrieveSalaire(@PathVariable("salaire-id") Long salaireId) {
	return salaireService.retrieveSalaire(salaireId);
	}
	
	// http://localhost:8082/banque-en-ligne/salaire/add-salaire
	@PostMapping("/add-salaire")
	@ResponseBody
	public Salaire addSalaire(@RequestBody Salaire t)
	{
	Salaire salaire = salaireService.addSalaire(t);
	return salaire;
	}
	
	
	// http://localhost:8082/banque-en-ligne/salaire/remove-salaire/{id-salaire}
	@DeleteMapping("/remove-salaire/{id-salaire}")
	@ResponseBody
	public void removeSalaire(@PathVariable("id-salaire") Long idSalaire) {
	salaireService.deleteSalaire(idSalaire);
	}
	
	
	// http://localhost:8082/banque-en-ligne/salaire/modify-salaire
	@PutMapping("/modify-salaire")
	@ResponseBody
	public Salaire modifySalaire(@RequestBody Salaire salaire) {
	return salaireService.updateSalaire(salaire);
	}
}
