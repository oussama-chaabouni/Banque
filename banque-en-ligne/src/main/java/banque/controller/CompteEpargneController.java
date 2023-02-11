package banque.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

import banque.entities.CompteEpargne;
import banque.repositories.CompteEpargneRepository;
import banque.services.CompteEpargneService;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/compteepargne")
public class CompteEpargneController {
	
	@Autowired
	CompteEpargneService compteepargneService;
	
	@Autowired
	CompteEpargneRepository CompteEpargneRep;

	
	// http://localhost:8082/banque-en-ligne/compteepargne/retrieve-all-compteepargnes
		@GetMapping("/retrieve-all-compteepargnes")
		@ResponseBody
		public List<CompteEpargne> getCompteEpargnes() {
		List<CompteEpargne> listCompteEpargnes = compteepargneService.retrieveAllCompteEpargnes();
		return listCompteEpargnes;
		}
		
		// http://localhost:8082/banque-en-ligne/compteepargne/retrieve-compteepargne/8
		@GetMapping("/retrieve-compteepargne/{compteepargne-id}")
		@ResponseBody
		public CompteEpargne retrieveCompteEpargne(@PathVariable("compteepargne-id") Long compteepargneId) {
		return compteepargneService.retrieveCompteEpargne(compteepargneId);
		}
		
		// http://localhost:8082/banque-en-ligne/compteepargne/add-compteepargne
		@PostMapping("/add-compteepargne")
		@ResponseBody
		public CompteEpargne addCompteEpargne(@RequestBody CompteEpargne t)
		{
		CompteEpargne compteepargne = compteepargneService.addCompteEpargne(t);
		return compteepargne;
		}
		
		
		// http://localhost:8082/banque-en-ligne/compteepargne/remove-compteepargne/{id-compteepargne}
		@DeleteMapping("/remove-compteepargne/{id-compteepargne}")
		@ResponseBody
		public void removeCompteEpargne(@PathVariable("id-compteepargne") Long idCompteEpargne) {
		compteepargneService.deleteCompteEpargne(idCompteEpargne);
		}
		
		
		// http://localhost:8082/banque-en-ligne/compteepargne/modify-compteepargne
		@PutMapping("/modify-compteepargne")
		@ResponseBody
		public CompteEpargne modifyCompteEpargne(@RequestBody CompteEpargne compteepargne) {
		return compteepargneService.updateCompteEpargne(compteepargne);
		}
		

		@GetMapping("/simulateurEpargneMensuel")
		public double SimulateurEpargneMensuel(@RequestParam("capitalinitial") String capitalinitial ,@RequestParam("versementmensuel") String versementmensuel, @RequestParam("rendementannuel") String rendementannuel, @RequestParam("dureeepargne") String dureeepargne)	{
			
			double capitalinitiall= Double.parseDouble(capitalinitial);
			double versementmensuell= Double.parseDouble(versementmensuel);	
			double rendementannuell= Double.parseDouble(rendementannuel);	
			double dureeepargnee= Double.parseDouble(dureeepargne);
				
			//el 0.8333 hiya 1/12
		double aa= Math.pow( 1+rendementannuell*0.01f,0.08333333333 );
		double capitaltotal=(capitalinitiall* Math.pow((1+ rendementannuell* 0.01f),dureeepargnee))+ versementmensuell*((Math.pow((1+aa -1  ),(dureeepargnee*12)))-1)/(Math.pow( 1+rendementannuell*0.01f,0.08333333333 )-1  ) ;
			return capitaltotal;
		}
		
		
		
		@GetMapping("/simulateurEpargneTrimestriel")
		public double SimulateurEpargneTrimestriel(@RequestParam("capitalinitial") String capitalinitial ,@RequestParam("versementmensuel") String versementmensuel, @RequestParam("rendementannuel") String rendementannuel, @RequestParam("dureeepargne") String dureeepargne)	{
			
			double capitalinitiall= Double.parseDouble(capitalinitial);
			double versementmensuell= Double.parseDouble(versementmensuel);	
			double rendementannuell= Double.parseDouble(rendementannuel);	
			double dureeepargnee= Double.parseDouble(dureeepargne);
				
			//el 0.25 hiya 1/4
		double aa= Math.pow( 1+rendementannuell*0.01f,0.25);
		double capitaltotal=(capitalinitiall* Math.pow((1+ rendementannuell* 0.01f),dureeepargnee))+ versementmensuell*((Math.pow((1+aa -1  ),(dureeepargnee*12)))-1)/(Math.pow( 1+rendementannuell*0.01f,0.25)-1  ) ;
			return capitaltotal;
		}
		
		
		
		@GetMapping("/simulateurEpargneSemestriel")
		public double SimulateurEpargneSemestriel(@RequestParam("capitalinitial") String capitalinitial ,@RequestParam("versementmensuel") String versementmensuel, @RequestParam("rendementannuel") String rendementannuel, @RequestParam("dureeepargne") String dureeepargne)	{
			
			double capitalinitiall= Double.parseDouble(capitalinitial);
			double versementmensuell= Double.parseDouble(versementmensuel);	
			double rendementannuell= Double.parseDouble(rendementannuel);	
			double dureeepargnee= Double.parseDouble(dureeepargne);
				
			//el 0.5 hiya 1/2 semestres
		double aa= Math.pow( 1+rendementannuell*0.01f,0.5 );
		double capitaltotal=(capitalinitiall* Math.pow((1+ rendementannuell* 0.01f),dureeepargnee))+ versementmensuell*((Math.pow((1+aa -1  ),(dureeepargnee*12)))-1)/(Math.pow( 1+rendementannuell*0.01f,0.5 )-1  ) ;
			return capitaltotal;
		}
		
		
		@GetMapping("/Livret_A")
		public void Livret_A()	{
			
			Random random = new Random();
		   
			//pour rib:
			int number1 = 1000000 + random.nextInt(9000000);
			String rib =  ""+ number1;
			//pour iban:
			int number2 = 100000 + random.nextInt(900000);
		    String ibane = "TN" + number2;
		    //Le dépôt initial est d’un montant minimum de 10 €. 
		    //Le solde du Livret A et du Livret Jeune doit en outre être de 10 € minimum. 
		    //Il ne peut être détenu qu’un Livret A et un Livret Jeune par personne
		    CompteEpargneRep.ajouterCompteEpargne( rib,ibane, 22950 ,10,3,"Livret_A","Kenza");		
		}
		
		@GetMapping("/LDDS")
		public void LDDS()	{
			
			Random random = new Random();
		   
			//pour rib:
			int number1 = 1000000 + random.nextInt(9000000);
			String rib =  ""+ number1;
			//pour iban:
			int number2 = 100000 + random.nextInt(900000);
		    String ibane = "TN" + number2;
		    
		    CompteEpargneRep.ajouterCompteEpargne( rib,ibane,12000,0,3,"LDDS","Kenza");		
		}
		
		//Il ne peut être détenu qu’un Livret A et un Livret Jeune par personne
		//80 TND offerts 
		//Le dépôt initial est d’un montant minimum de 10 
		@GetMapping("/Livret_JEUNE")
		public void Livret_JEUNE()	{
			
			Random random = new Random();
		   
			//pour rib:
			int number1 = 1000000 + random.nextInt(9000000);
			String rib =  ""+ number1;
			//pour iban:
			int number2 = 100000 + random.nextInt(900000);
		    String ibane = "TN" + number2;
		    
		    CompteEpargneRep.ajouterCompteEpargne( rib,ibane,1600,80,3,"Livret_JEUNE","Kenza");		
		}
		
		//Versement initial À l'ouverture, vous devez verser 30 € minimum sur votre livret LEP.
		@GetMapping("/LEP")
		public void LEP()	{
			
			Random random = new Random();
		   
			//pour rib:
			int number1 = 1000000 + random.nextInt(9000000);
			String rib =  ""+ number1;
			//pour iban:
			int number2 = 100000 + random.nextInt(900000);
		    String ibane = "TN" + number2;
		    
		    CompteEpargneRep.ajouterCompteEpargne( rib,ibane,7700,0, 6.10f,"LEP","Kenza");		
		}
		
		
	//sans plafond
	//L'ouverture du compte est possible avec un minimum de 10 €.
		@GetMapping("/Compte_Epargne")
		public void Compte_Epargne()	{
			
			Random random = new Random();
		   
			//pour rib:
			int number1 = 1000000 + random.nextInt(9000000);
			String rib =  ""+ number1;
			//pour iban:
			int number2 = 100000 + random.nextInt(900000);
		    String ibane = "TN" + number2;
		    
		    CompteEpargneRep.ajouterCompteEpargne( rib,ibane,0,10, 0.01f,"Compte_Epargne","Kenza");		
		}
		
		
		
		
}