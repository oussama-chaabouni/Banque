package banque.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import banque.entities.Formulairesouscription;
import banque.entities.Titredecivilite;
import banque.repositories.FormulairesouscriptionRepository;
import banque.services.FormulairesouscriptionService;
import banque.services.FormulairesouscriptionService;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/formulairesouscription")
public class FormulairesouscriptionController {
	
	@Autowired
	FormulairesouscriptionService formulairesouscriptionService;
	
	@Autowired
	FormulairesouscriptionRepository formulairesouscriptionRep;
	
	// http://localhost:8082/banque-en-ligne/formulairesouscription/retrieve-all-formulairesouscriptions
		@GetMapping("/retrieve-all-formulairesouscriptions")
		@ResponseBody
		public List<Formulairesouscription> getFormulairesouscriptions() {
		List<Formulairesouscription> listFormulairesouscriptions = formulairesouscriptionService.retrieveAllFormulairesouscriptions();
		return listFormulairesouscriptions;
		}
		
		// http://localhost:8082/banque-en-ligne/formulairesouscription/retrieve-formulairesouscription/8
		@GetMapping("/retrieve-formulairesouscription/{id}")
		@ResponseBody
		public Formulairesouscription retrieveFormulairesouscription(@PathVariable("id") Long Id) {
		return formulairesouscriptionService.retrieveFormulairesouscription(Id);
		}
		
		// http://localhost:8082/banque-en-ligne/formulairesouscription/add-formulairesouscription
		@PostMapping("/add-formulairesouscription")
		@ResponseBody
		public Formulairesouscription addFormulairesouscription(@RequestBody Formulairesouscription t)
		{
		Formulairesouscription formulairesouscription = formulairesouscriptionService.addFormulairesouscription(t);
		return formulairesouscription;
		}
		
		
		// http://localhost:8082/banque-en-ligne/formulairesouscription/remove-formulairesouscription/{id}
		@DeleteMapping("/remove-formulairesouscription/{id}")
		@ResponseBody
		public void removeFormulairesouscription(@PathVariable("id") Long id) {
		formulairesouscriptionService.deleteFormulairesouscription(id);
		}
		
		
		// http://localhost:8082/banque-en-ligne/formulairesouscription/modify-formulairesouscription
		@PutMapping("/modify-formulairesouscription")
		@ResponseBody
		public Formulairesouscription modifyFormulairesouscription(@RequestBody Formulairesouscription formulairesouscription) {
		return formulairesouscriptionService.updateFormulairesouscription(formulairesouscription);
		}
		

		@PostMapping("/formulairesouscription")
		@ResponseBody
		public void formulairesouscription (@RequestParam("titredecivilite") String titredecivilite,  @RequestParam("nom") String nom,@RequestParam("prenom") String prenom, @RequestParam("datenaissance") Date datenaissance, @RequestParam("numero") long numero, @RequestParam("email") String email,@RequestParam("titredecivilitetuteur") String titredecivilitetuteur,  @RequestParam("nomtuteur") String nomtuteur,@RequestParam("prenomtuteur") String prenomtuteur, @RequestParam("datenaissancetuteur") String datenaissancetuteur, @RequestParam("numerotuteur") String numerotuteur, @RequestParam("emailtuteur") String emailtuteur,    @RequestParam("pieceditentite") String pieceditentite, @RequestParam("justificatifdedomicile") String justificatifdedomicile, @RequestParam("depotinitial") long depotinitial){
			
			formulairesouscriptionRep.ajouterFormulairesouscription(titredecivilite, nom, prenom, datenaissance, numero, email,titredecivilitetuteur, nomtuteur,  prenomtuteur, datenaissancetuteur, numerotuteur, emailtuteur, pieceditentite, justificatifdedomicile, depotinitial);
		}




}
