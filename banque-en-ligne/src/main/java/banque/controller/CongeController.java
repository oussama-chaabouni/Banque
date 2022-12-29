package banque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import banque.entities.Assurance;
import banque.entities.Conge;
import banque.services.ICongeService;

@RestController
@RequestMapping("/Conge")
@CrossOrigin(origins = "*")
public class CongeController {
	@Autowired
	ICongeService congeService;
	
	@PostMapping("/add-conge/{idemploye}")
	@ResponseBody
	public Conge addConge(@RequestBody Conge c,@PathVariable("idemploye") Long ide)
	{
		return congeService.addConge(c, ide);
	}
	
	
	@GetMapping("/retrieve-all-conges")
	@ResponseBody
	public List<Conge> getConge() {
	List<Conge> listConges = congeService.retrieveAllConges();
	return listConges;
	}
	
	@PutMapping("/modify-Conge/{idemploye}")
	@ResponseBody
	public Conge modifyConge(@RequestBody Conge c,@PathVariable("idemploye") Long ide) {
	return congeService.addConge(c,ide);
	}
	
	
	

	@DeleteMapping("/remove-Conge/{conge-id}")
	@ResponseBody
	public void deleteConge(@PathVariable("conge-id") Long congeId) {
	congeService.deleteConge(congeId);
	}
	
	@PutMapping("/accept/{conge-id}")
	@ResponseBody
	public void acceptConge(@RequestBody Conge conge,@PathVariable("conge-id") Long congeId) {
		
			 congeService.acceptConge(conge,congeId);
			
		
		
	}
	
}
