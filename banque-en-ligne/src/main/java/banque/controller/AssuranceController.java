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

import banque.entities.Assurance;
import banque.entities.Credit;
import banque.services.AssuranceService;
import banque.services.IAssurance;
import banque.services.ICongeService;
import banque.services.ICreditService;



@RestController
@RequestMapping("/Assurance")
public class AssuranceController {
	@Autowired
	IAssurance assuranceService;
	@Autowired
	ICreditService crediService;
	@CrossOrigin(origins = "http://localhost:4200/")
	
	
	@PostMapping("/add-assurance")
	@ResponseBody
	public Assurance addAssurance(@RequestBody Assurance a)
	{
	Assurance assurance = assuranceService.addAssurance(a);
	
	return assurance;
	}
	@CrossOrigin(origins = "http://localhost:4200/")
	@GetMapping("/retrieve-all-assurances")
	@ResponseBody
	public List<Assurance> getAssurance() {
	List<Assurance> listAssurances = assuranceService.retrieveAllAssurances();
	return listAssurances;
	}
	
	
	
		@PutMapping("/modify-Assurance")
		@ResponseBody
		public Assurance modifyAsurance(@RequestBody Assurance assurance) {
		return assuranceService.addAssurance(assurance);
		}
		
		
		@DeleteMapping("/remove-Assurance/{assurance-id}")
		@ResponseBody
		public void deleteAssurance(@PathVariable("assurance-id") Long assuranceId) {
		assuranceService.deleteAssurance(assuranceId);
		}
		
		
		@PostMapping("/SuggestionAssurance")
		@ResponseBody
		public String suggestionAssurance (@RequestBody Credit c, Assurance a) 
		{
		return assuranceService.suggestionAssurance(c, a);
		}
		
		@GetMapping("/getDesc/{credit-id}")
		@ResponseBody
		public String getDescById(@PathVariable("credit-id")Long idCredit){
		
		return crediService.getDescById(idCredit);
		}
		@CrossOrigin(origins = "http://localhost:4200/")
		@GetMapping("/sugg/{credit-id}")
		@ResponseBody
		public String suggest(@PathVariable("credit-id")Long idCredit){
		
		return assuranceService.suggest(idCredit);
		}
		
		
}
