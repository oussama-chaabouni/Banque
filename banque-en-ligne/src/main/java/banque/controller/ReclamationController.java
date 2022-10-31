package banque.controller;

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

import banque.entities.Reclamation;
import banque.services.ReclamationService;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/reclamation")
public class ReclamationController {
	
	@Autowired
	ReclamationService reclamationService;
	
	// http://localhost:8082/banque-en-ligne/reclamation/retrieve-all-reclamations
	@GetMapping("/retrieve-all-reclamations")
	@ResponseBody
	public List<Reclamation> getReclamations() {
	List<Reclamation> listReclamations = reclamationService.retrieveAllReclamations();
	return listReclamations;
	}
	
	// http://localhost:8082/banque-en-ligne/reclamation/retrieve-reclamation/8
	@GetMapping("/retrieve-reclamation/{reclamation-id}")
	@ResponseBody
	public Reclamation retrieveReclamation(@PathVariable("reclamation-id") Long reclamationId) {
	return reclamationService.retrieveReclamation(reclamationId);
	}
	
	// http://localhost:8082/banque-en-ligne/reclamation/add-reclamation
	@PostMapping("/add-reclamation")
	@ResponseBody
	public Reclamation addReclamation(@RequestBody Reclamation t)
	{
	Reclamation reclamation = reclamationService.addReclamation(t);
	return reclamation;
	}
	
	
	// http://localhost:8082/banque-en-ligne/reclamation/remove-reclamation/{reclamation-id}
	@DeleteMapping("/remove-reclamation/{reclamation-id}")
	@ResponseBody
	public void removeReclamation(@PathVariable("reclamation-id") Long reclamationId) {
	reclamationService.deleteReclamation(reclamationId);
	}
	
	// http://localhost:8082/banque-en-ligne/reclamation/modify-reclamation
	@PutMapping("/modify-reclamation")
	@ResponseBody
	public Reclamation modifyReclamation(@RequestBody Reclamation reclamation) {
	return reclamationService.updateReclamation(reclamation);
	}

}
