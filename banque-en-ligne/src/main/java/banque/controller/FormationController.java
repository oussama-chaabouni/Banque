package banque.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.mailjet.client.errors.MailjetException;

import banque.entities.Formation;
import banque.entities.Formation_Details;
import banque.services.FormationService;
import banque.services.MailService;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/formation")
public class FormationController {
	
	@Autowired 
	FormationService Fs;
	@Autowired
	MailService Ms;
	
	
	/* liste des formations */
	@GetMapping("/retrieve-all-formations")
	public List <Formation> getFormations() throws MailjetException{
		//Ms.envoyerMailPersonnelDateExamen();
	return  Fs.findall();
	}
		
	// Ajout formation
	@PostMapping("/add-formation")
	public Formation addFormation(@Valid @RequestBody Formation f )
	{ 
			
		return Fs.save(f);
	}
		   
	// http://localhost:8082/banque-en-ligne/formation/modify-formation
	@PutMapping("/modify-formation")
	@ResponseBody
	public Formation modifyFormation(@RequestBody Formation f) {
	return Fs.save(f);
	}
		
    //supprimer formation*/
	@DeleteMapping("/remove-formation/{id}")
	public void removeFormation(@PathVariable(name="id") Long id){
		Fs.delete(id);
				
	}
	
	// participer dans une formation en utilisant id formation et id employe*/
	@PostMapping("/participer/{idF}/{idp}")
	public Formation_Details ParticiperFormation(@PathVariable(name="idF") long id,@PathVariable(name="idp") long idp )
	{ 
			
		return Fs.ParticiperFormation(id,idp);
	}
	
	/* liste de mes formations  en utilisant id employee*/
	@GetMapping(value="/findallMyFormation/{idp}")
	public List <Formation> findallMyformation(@PathVariable(name="idp") long idp){	
		return  Fs.ListeFormationParIdEmploye(idp);
	}
		
	//  connaitre les employees participée au formation en utilisant id formation
	@GetMapping(value="/afficherEmployesParticipant/{idf}")
	public List <Formation_Details> afficherEmployesParticipants(@PathVariable(name="idf") Long id){	
		return  Fs.ParticiperAuformation(id);
	}
			
	// Desinscrire l'employee du formation
	@DeleteMapping(value="/desinscrire/{idp}/{idf}")
	public void  desincrireFormation(@PathVariable(name="idp") long idp,@PathVariable(name="idf") long idf){
		Fs.desinscrireFormation(idp, idf);
			
	}
			
			
			
	
	
	

}
