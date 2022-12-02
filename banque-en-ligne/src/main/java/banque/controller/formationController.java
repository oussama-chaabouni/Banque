package banque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import banque.entities.Formation;
import banque.repositories.FormationRepository;
import banque.services.FormationService;

@RestController
@RequestMapping("/formation")
public class formationController {
	
	@Autowired 
	FormationService Fs;
	
	
	@Autowired 
	FormationRepository rs;
	
	// Ajout formation
	   @PostMapping("/ajout")
		public Formation create( @RequestBody Formation f )
		{ 
		
			return Fs.save(f);
		}
	   
	   /* liste des formations */
	// http://localhost:8082/banque-en-ligne/transaction/retrieve-all-formations
		@GetMapping("/retrieve-all-formations")
		@ResponseBody
		public List<Formation> getFormations() {
		List<Formation> listFormations = (List<Formation>) rs.findAll();
		return listFormations;
		}
}
