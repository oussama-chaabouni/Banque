package banque.controller;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import banque.entities.CompteCourant;
import banque.services.CompteCourantService;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/comptecourant")
public class CompteCourantController {
	
	@Autowired
	CompteCourantService comptecourantService;
	
	// http://localhost:8082/banque-en-ligne/comptecourant/retrieve-all-comptecourants
		@GetMapping("/retrieve-all-comptecourants")
		@ResponseBody
		public List<CompteCourant> getCompteCourants() {
		List<CompteCourant> listCompteCourants = comptecourantService.retrieveAllCompteCourants();
		return listCompteCourants;
		}
		
		// http://localhost:8082/banque-en-ligne/comptecourant/retrieve-comptecourant/8
		@GetMapping("/retrieve-comptecourant/{comptecourant-id}")
		@ResponseBody
		public CompteCourant retrieveCompteCourant(@PathVariable("comptecourant-id") Long comptecourantId) {
		return comptecourantService.retrieveCompteCourant(comptecourantId);
		}
		
		// http://localhost:8082/banque-en-ligne/comptecourant/add-comptecourant
		@PostMapping("/add-comptecourant")
		@ResponseBody
		public CompteCourant addCompteCourant(@RequestBody CompteCourant t)
		{
		CompteCourant comptecourant = comptecourantService.addCompteCourant(t);
		return comptecourant;
		}
		
		
		// http://localhost:8082/banque-en-ligne/comptecourant/remove-comptecourant/{id-comptecourant}
		@DeleteMapping("/remove-comptecourant/{id-comptecourant}")
		@ResponseBody
		public void removeCompteCourant(@PathVariable("id-comptecourant") Long idCompteCourant) {
		comptecourantService.deleteCompteCourant(idCompteCourant);
		}
		
		
		// http://localhost:8082/banque-en-ligne/comptecourant/modify-comptecourant
		@PutMapping("/modify-comptecourant")
		@ResponseBody
		public CompteCourant modifyCompteCourant(@RequestBody CompteCourant comptecourant) {
		return comptecourantService.updateCompteCourant(comptecourant);
		}
		
		





}
