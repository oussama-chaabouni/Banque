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

import banque.entities.Conge;
import banque.entities.Emplois;
import banque.services.ICongeService;
import banque.services.IEmploisService;

@RestController
@RequestMapping("/Emplois")
public class EmploisController {
	@Autowired
	IEmploisService emploisService;
	@CrossOrigin(origins = "http://localhost:4200/")
	
	
	@PostMapping("/add-emploi")
	@ResponseBody
	public Emplois addEmplois(@RequestBody Emplois e)
	{
	Emplois emploi = emploisService.addEmplois(e);
	return emploi;
	}
	
	
	
	
	
	
	@CrossOrigin(origins = "http://localhost:4200/")
	@GetMapping("/retrieve-all-emplois")
	@ResponseBody
	public List<Emplois> getEmploi() {
	List<Emplois> listEmplois = emploisService.retrieveAllEmplois();
	return listEmplois;
	}
	
	@PutMapping("/modify-Emplois")
	@ResponseBody
	public Emplois modifyEmploi(@RequestBody Emplois emploi) {
	return emploisService.addEmplois(emploi);
	}
	
	
	

	@DeleteMapping("/remove-Emploi/{emploi-id}")
	@ResponseBody
	public void deleteEmploi(@PathVariable("emploi-id") Long emploiId) {
	emploisService.deleteEmplois(emploiId);
	}
}
