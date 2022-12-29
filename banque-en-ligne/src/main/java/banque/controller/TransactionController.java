package banque.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.mailjet.client.errors.MailjetException;

import banque.entities.Client;
import banque.entities.CompteCourant;
import banque.entities.Transaction;
import banque.entities.TypeTransaction;
import banque.repositories.CompteCourantRepository;
import banque.repositories.CompteEpargneRepository;
import banque.repositories.PayementRepository;
import banque.repositories.TransactionRepository;
import banque.services.ReclamationService;
import banque.services.TransactionService;
import banque.services.GlobalRestService;
import banque.services.GlobalRestServiceEpargne;
import banque.services.GlobalRestServiceVirementPermanent;
import banque.services.GlobalRestServiceVirementPermanentEpargne;
import banque.services.MailService;
import banque.scheduler.JobData;
import banque.scheduler.JobDataVirementPermanent;


@RestController
@CrossOrigin(origins="*")
@RequestMapping("/transaction")
public class TransactionController {
	
	LocalDateTime currentDateTime = LocalDateTime.now();
	
	
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	TransactionRepository transactionRep;
	
	@Autowired
	CompteCourantRepository compteCourantRep;
	
	@Autowired
	CompteEpargneRepository compteEpargneRep;
	
	@Autowired
	PayementRepository payementRep;
	
	@Autowired
	ReclamationService reclamationService;
	
	@Autowired
	GlobalRestService globalRestService;
	
	@Autowired
	GlobalRestServiceEpargne globalRestServiceEpargne;
	
	@Autowired
	GlobalRestServiceVirementPermanent globalRestServiceVirementPermanent;
	
	@Autowired
	GlobalRestServiceVirementPermanentEpargne globalRestServiceVirementPermanentEpargne;
	
	@Autowired
	MailService Ms;
	
	
	Client client;
	
	
	
	// http://localhost:8082/banque-en-ligne/transaction/retrieve-all-transactions
	@GetMapping("/retrieve-all-transactions")
	@ResponseBody
	public List<Transaction> getTransactions() {
	List<Transaction> listTransactions = transactionService.retrieveAllTransactions();
	return listTransactions;
	}
	
	// http://localhost:8082/banque-en-ligne/transaction/retrieve-transaction/8
	@GetMapping("/retrieve-transaction/{transaction-id}")
	@ResponseBody
	public Transaction retrieveTransaction(@PathVariable("transaction-id") Long transactionId) {
	return transactionService.retrieveTransaction(transactionId);
	}
	
	// http://localhost:8082/banque-en-ligne/transaction/add-transaction
	@PostMapping("/add-transaction")
	@ResponseBody
	public Transaction addTransaction(@RequestBody Transaction t)
	{
	Transaction transaction = transactionService.addTransaction(t);
	return transaction;
	}
	
	
	// http://localhost:8082/banque-en-ligne/transaction/remove-transaction/{id-transaction}
	@DeleteMapping("/remove-transaction/{id-transaction}")
	@ResponseBody
	public void removeTransaction(@PathVariable("id-transaction") Long idTransaction) {
	transactionService.deleteTransaction(idTransaction);
	}
	
	
	// http://localhost:8082/banque-en-ligne/transaction/modify-transaction
	@PutMapping("/modify-transaction")
	@ResponseBody
	public Transaction modifyTransaction(@RequestBody Transaction transaction) {
	return transactionService.updateTransaction(transaction);
	}
	
	//JPQL
	
	// http://localhost:8082/banque-en-ligne/transaction/retrieve-transactionJPQL/1
	@GetMapping("/retrieve-transactionJPQL/{typeTransaction}")
	@ResponseBody
	public List<Transaction> retrieveTrsByTypeTransactionJPQL(@PathVariable("typeTransaction") TypeTransaction typeTransaction) {
	List<Transaction> listTransactionsJPQL = transactionService.retrieveTransactsByTypeTransaction(typeTransaction);
	return listTransactionsJPQL ;
	}
	
	// http://localhost:8082/banque-en-ligne/transaction/modify-transactionJPQL/ / 
	@PutMapping("/modify-transactionJPQL/{typeTransaction}/{rib}")
	@ResponseBody
	public int updateTypeTrBycompteCourantIdJPQL(@PathVariable("typeTransaction") TypeTransaction typeTransaction ,@PathVariable("rib") String rib) {
		return transactionService.updateTypeTransactBycompteCourantRib(typeTransaction ,rib);
	}
	
	// http://localhost:8082/banque-en-ligne/transaction/delete-transactionJPQL/ / 
		@DeleteMapping("/delete-transactionJPQL/{typeTransaction}/{rib}")
		@ResponseBody
	public int deleteTransactByTypeTransactionAndSourceJPQL(@PathVariable("typeTransaction")TypeTransaction typeTransaction,@PathVariable("ribCompteCourant")String rib) {
		return transactionService.deleteTransactByTypeTransactionAndRibc( typeTransaction,rib);
	}
		
		
///////////NEST7A9HOM ANGULAR		
		
		
		@GetMapping("/getsoldecomptecourant/{rib}")
		@ResponseBody
		public float getSoldeCompteCourant(@PathVariable String rib)
	    {
	        return compteCourantRep.getSoldeCompteCourant(rib) ;
	    }
		
		@PutMapping("/ChangeSoldeCompteCourantByRib/{new_solde}/{rib}")
		@ResponseBody
	    void ChangeSoldeCompteCourantByRib(@PathVariable float new_solde, @PathVariable String rib) {
			compteCourantRep.ChangeSoldeCompteCourantByRib(new_solde, rib);
		}
		
//////////	
	
	  
/////MICRO SERVICES
		
	//byRibUserConnecte	
	@GetMapping("/retrievelisttransactionsByRib")
	public List<Transaction> retrievelisttransactionsByRib (@RequestParam("rib") String rib){
		
		List<Transaction> listTransactionsByName = transactionRep.retrievelisttransactionsByRib(rib);
		return listTransactionsByName;
	}
	
				
		
	
	//li fel param mta3 el methode houma li bech ndakhalhom
	@PostMapping("/depot")
	public String depot (@RequestParam("deposit_amount") String depositAmount, @RequestParam("monRib") String monRib){//,HttpSession session
		
		//client = (Client) session.getAttribute("client");
		
		long rib= Long.parseLong(monRib); //bech el compte courantId li fel parametrz nraja3ha long
		float depositMontantValue = Float.parseFloat(depositAmount);
		
		//float currentSolde = compteCourantRep.getSoldeCompteCourantt("Kenza",monRib); //client.getNom()
		float currentSolde = compteCourantRep.getSoldeCompteCourant(monRib);				
		
		if(depositMontantValue == 0) {
			transactionRep.ajouterTransaction(monRib,monRib, "Dépot", depositMontantValue, "Dépot", "dépot sans succés", "montant de dépot = 0", currentDateTime);  
			
			//transactionId bech najem n3amer colonne transactionid f table reclamation
			long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(monRib);
			
			reclamationService.ajouterRec(monRib, "Dépot", depositMontantValue,"Dépot", "dépot sans succés", "montant de dépot = 0", currentDateTime);		
			
			return "deposit amount value =0";
			
		}
		
		
		//Découvert
		//CALCUL AGIOS
		if(depositMontantValue >=1 && (currentSolde<0 && currentSolde>-1200)) //NEKHOU ALIH INTERET
		{
			
			float currentSoldeàdecouvert = compteCourantRep.getSoldeCompteCourant(monRib);
			float montantdudecouvert= 0- currentSoldeàdecouvert; 
			float  tauxAnnuelEffectifGlobal=0.12f; //12%
			
			long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(monRib);
			LocalDateTime dateDebutDecouvert=transactionRep.getDateDebutDuDecouvert(transactionId);
			long duréedudecouvert = ChronoUnit.DAYS.between(dateDebutDecouvert,currentDateTime);//currentdatetime - ekher localdatetime 3mal fiha transaction
			//localdatetime(idcomptecourant, depot) hedhi asa7 melli ta7tha
			
			int nombredejoursdanslannée=365 ; 
		
			//AGIOS = (montant du découvert x durée du découvert x taux de la banque)/nombre de jours dans l’année
			float AGIOS = ((montantdudecouvert * duréedudecouvert * tauxAnnuelEffectifGlobal)/ nombredejoursdanslannée) ;
			float newSoldeApresDecouvert = currentSolde + depositMontantValue-AGIOS;
			
			compteCourantRep.ChangeSoldeCompteCourantByRib(newSoldeApresDecouvert,monRib);
			transactionRep.ajouterTransaction(monRib,monRib, "Dépot", depositMontantValue,"---", "dépot avec succés", "durée du decouvert "+duréedudecouvert, currentDateTime);  
			
			return AGIOS +"/" ;
		}	
		
		//update comptecourant
		//update solde
		float newSolde = currentSolde + depositMontantValue;
		compteCourantRep.ChangeSoldeCompteCourantByRib(newSolde,monRib);
		//Save f table transaction
		transactionRep.ajouterTransaction(monRib,monRib, "Dépot", depositMontantValue,"---", "dépot avec succés", "montant de dépot = "+ depositMontantValue, currentDateTime);  
		
		return "depot avec succés";
	
		
	}
	
	@PostMapping("/depotEpargne")
	public String depotEpargne (@RequestParam("deposit_amount") String depositAmount, @RequestParam("monRib") String monRib){
		//Get Currrent COMPTE COURANT BALANCE:
		long rib= Long.parseLong(monRib); //bech el compte courantId li fel parametrz nraja3ha long
		float depositMontantValue = Float.parseFloat(depositAmount);
		float currentSolde = compteEpargneRep.getSoldeCompteEpargne(monRib);
						
		//check if deposit amount =0
		if(depositMontantValue == 0) {
			transactionRep.ajouterTransaction(monRib,monRib, "Dépot", depositMontantValue, "---", "dépot sans succés", "montant de dépot = 0", currentDateTime);  
			
			//transactionId bech najem n3amer colonne transactionid f table reclamation
			long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(monRib);
			
			reclamationService.ajouterRec(monRib, "Dépot", depositMontantValue,"---", "dépot sans succés", "montant de dépot = 0", currentDateTime);		
			
			return "deposit amount value =0";			
		}
	
		
		//Découvert
		//CALCUL AGIOS
		if(depositMontantValue >=1 && (currentSolde<0 && currentSolde>-1200)) //NEKHOU ALIH INTERET
		{
			
			float currentSoldeàdecouvert = compteEpargneRep.getSoldeCompteEpargne(monRib);
			float montantdudecouvert= 0- currentSoldeàdecouvert; 
			float  tauxAnnuelEffectifGlobal=0.12f; //12%
			
			long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(monRib);
			LocalDateTime dateDebutDecouvert=transactionRep.getDateDebutDuDecouvert(transactionId);
			long duréedudecouvert = ChronoUnit.DAYS.between(dateDebutDecouvert,currentDateTime);//currentdatetime - ekher localdatetime 3mal fiha transaction
			//localdatetime(idcomptecourant, depot) hedhi asa7 melli ta7tha
			
			int nombredejoursdanslannée=365 ; 
		
			//AGIOS = (montant du découvert x durée du découvert x taux de la banque)/nombre de jours dans l’année
			float AGIOS = ((montantdudecouvert * duréedudecouvert * tauxAnnuelEffectifGlobal)/ nombredejoursdanslannée) ;
			float newSoldeApresDecouvert = currentSolde + depositMontantValue-AGIOS;
			
			compteEpargneRep.ChangeSoldeCompteEpargneByRib(newSoldeApresDecouvert,monRib);
			transactionRep.ajouterTransaction(monRib,monRib, "Dépot", depositMontantValue,"---", "dépot avec succés", "durée du decouvert "+duréedudecouvert, currentDateTime);  
			
			return "AGIOS "+AGIOS +" durée du decouvert" +duréedudecouvert +"jours" ;
		}	
		
		//update comptecourant
		//update solde
		float newSolde = currentSolde + depositMontantValue;
		compteEpargneRep.ChangeSoldeCompteEpargneByRib(newSolde,monRib);
		//Save f table transaction
		transactionRep.ajouterTransaction(monRib,monRib, "Dépot", depositMontantValue,"---", "dépot avec succés", "montant de dépot = "+ depositMontantValue, currentDateTime);  

		return "depot avec succés";
	
		
	}
	
	//VIREMENT IMMEDIAT
	@PostMapping("/transfer")
	public String transfer (@RequestParam("transferFromRib") String transferFromRib,  @RequestParam("transferToRib") String transferToRib,@RequestParam("transfer_amount") String transferAmount, @RequestParam("motif") String motif){
		//Get Currrent COMPTE COURANT BALANCE:
				
		//long transferFrom= Long.parseLong(transferFromRib); //bech el compte courantId li fel parametrz nraja3ha long
		//long transferTo= Long.parseLong(transferToRib);
				
				int transferMontant = Integer.parseInt(transferAmount);
				
				float currentSoldeofCompteCourantTransferingFrom = compteCourantRep.getSoldeCompteCourant(transferFromRib);
				float currentSoldeofCompteCourantTransferingTo = compteCourantRep.getSoldeCompteCourant(transferToRib);
							
		
//check if transfering into the same compteCourant
				if(transferFromRib.equals(transferToRib)) {

			transactionRep.ajouterTransaction(transferFromRib,transferToRib, "Virement_Immédiat", transferMontant, motif, "Echec du virement immédiat", "envoi au meme compte", currentDateTime);  

			long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(transferFromRib);
			reclamationService.ajouterRec(transferFromRib, "Virement_Immédiat", transferMontant,motif, "Echec du virement immédiat", "envoi au meme compte", currentDateTime);
			
			return "cannot transfer to the same Account"; }
		
//check if transfer amount =0
		if(transferMontant == 0) {

			transactionRep.ajouterTransaction(transferFromRib,transferToRib, "Virement_Immédiat", transferMontant,motif, "Echec du virement immédiat", "Montant de transfer egale à 0", currentDateTime);  

			long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(transferFromRib);
			reclamationService.ajouterRec(transferFromRib, "Virement_Immédiat", transferMontant,motif,"Echec du virement immédiat","Montant de transfer egale à 0", currentDateTime);

			return "transfer amount value =0 , please enter a value greater than 0";
		}
		//check if transfer amount >currentsolde
		if(transferMontant > currentSoldeofCompteCourantTransferingFrom ) {
			
			transactionRep.ajouterTransaction(transferFromRib,transferToRib, "Virement_Immédiat", transferMontant,motif, "Echec du virement immédiat", "Montant Transferé est superieur au solde", currentDateTime);  
			
			long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(transferFromRib);
			reclamationService.ajouterRec(transferFromRib, "Virement_Immédiat", transferMontant,motif, "Echec du virement immédiat","Montant Transferé est superieur au solde", currentDateTime);

			
			return "impossible de transferer un montant superieur à celui dans votre compte";		
			}
		
		if(transferMontant>=15000) {
			
			transactionRep.ajouterTransaction(transferFromRib,transferToRib, "Virement_Immédiat", transferMontant,motif, "Echec du virement immédiat", "15 000 dinars maximum par virement", currentDateTime);  

			long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(transferFromRib);
			reclamationService.ajouterRec(transferFromRib, "Virement_Immédiat", transferMontant,motif,"Echec du virement immédiat","15 000 dinars maximum par virement", currentDateTime);

			return "15 000 dinars maximum par virement";
			
					}

		
		
//SINON: update solde
		float newSoldetransferFrom = currentSoldeofCompteCourantTransferingFrom - transferMontant;
		float newSoldetransferTo = currentSoldeofCompteCourantTransferingTo + transferMontant;
		
		//update comptecourant
		compteCourantRep.ChangeSoldeCompteCourantByRib(newSoldetransferFrom, transferFromRib);
		compteCourantRep.ChangeSoldeCompteCourantByRib(newSoldetransferTo, transferToRib);
		
		transactionRep.ajouterTransaction(transferFromRib,transferToRib, "Virement_Immédiat", transferMontant,motif, "virement immédiat effectué avec succès", transferMontant +" Dinars Transféré", currentDateTime );  
		
		
		
		return "montant transferé avec succés";
	}
	
	//VIREMENT IMMEDIAT EPARGNE
		@PostMapping("/transferEpargne")
		public String transferEpargne (@RequestParam("transferFromRib") String transferFromRib,  @RequestParam("transferToRib") String transferToRib,@RequestParam("transfer_amount") String transferAmount, @RequestParam("motif") String motif){
			//Get Currrent COMPTE COURANT BALANCE:
					//long transferFrom= Long.parseLong(transferFromRib); //bech el compte courantId li fel parametrz nraja3ha long
					//long transferTo= Long.parseLong(transferToRib);
					
					int transferMontant = Integer.parseInt(transferAmount);
					
					float currentSoldeofCompteEpargneTransferingFrom = compteEpargneRep.getSoldeCompteEpargne(transferFromRib);
					float currentSoldeofCompteEpargneTransferingTo = compteEpargneRep.getSoldeCompteEpargne(transferToRib);
								
			
	//check if transfering into the same compteCourant
				if(transferFromRib.equals(transferToRib)) {

				transactionRep.ajouterTransaction(transferFromRib,transferToRib, "Virement_Immédiat", transferMontant, motif, "Echec du virement immédiat", "envoi au meme compte", currentDateTime);  

				long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(transferFromRib);
				reclamationService.ajouterRec(transferFromRib, "Virement_Immédiat", transferMontant,motif, "Echec du virement immédiat", "envoi au meme compte", currentDateTime);
				
				return "cannot transfer to the same Account"; }
			
	//check if transfer amount =0
			if(transferMontant == 0) {

				transactionRep.ajouterTransaction(transferFromRib,transferToRib, "Virement_Immédiat", transferMontant,motif, "Echec du virement immédiat", "Montant de transfer egale à 0", currentDateTime);  

				long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(transferFromRib);
				reclamationService.ajouterRec(transferFromRib, "Virement_Immédiat", transferMontant,motif,"Echec du virement immédiat","Montant de transfer egale à 0", currentDateTime);

				return "transfer amount value =0 , please enter a value greater than 0";
			}
			//check if transfer amount >currentsolde
			if(transferMontant > currentSoldeofCompteEpargneTransferingFrom ) {
				
				transactionRep.ajouterTransaction(transferFromRib,transferToRib, "Virement_Immédiat", transferMontant,motif, "Echec du virement immédiat", "Montant Transferé est superieur au solde", currentDateTime);  
				
				long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(transferFromRib);
				reclamationService.ajouterRec(transferFromRib, "Virement_Immédiat", transferMontant,motif, "Echec du virement immédiat","Montant Transferé est superieur au solde", currentDateTime);

				
				return "impossible de transferer un montant superieur à celui dans votre compte";		
				}
			
			if(transferMontant>=15000) {
				
				transactionRep.ajouterTransaction(transferFromRib,transferToRib, "Virement_Immédiat", transferMontant,motif, "Echec du virement immédiat", "15 000 dinars maximum par virement", currentDateTime);  

				long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(transferFromRib);
				reclamationService.ajouterRec(transferFromRib, "Virement_Immédiat", transferMontant,motif,"Echec du virement immédiat","15 000 dinars maximum par virement", currentDateTime);

				return "15 000 dinars maximum par virement";
				
						}

			
			
	//SINON: update solde
			float newSoldetransferFrom = currentSoldeofCompteEpargneTransferingFrom - transferMontant;
			float newSoldetransferTo = currentSoldeofCompteEpargneTransferingTo + transferMontant;
			
			//update comptecourant
			compteEpargneRep.ChangeSoldeCompteEpargneByRib(newSoldetransferFrom, transferFromRib);
			compteEpargneRep.ChangeSoldeCompteEpargneByRib(newSoldetransferTo, transferToRib);
			
			transactionRep.ajouterTransaction(transferFromRib,transferToRib, "Virement_Immédiat", transferMontant,motif, "virement immédiat effectué avec succès", transferMontant +" Dinars Transféré", currentDateTime );  
			
			
			
			return "montant transferé avec succés";
		}
		
		//VIREMENT IMMEDIAT
		@PostMapping("/transferFromCompteCourantToCompteEpargne")
		public String transferFromCompteCourantToCompteEpargne (@RequestParam("transferFromRib") String transferFromRib,  @RequestParam("transferToRib") String transferToRib,@RequestParam("transfer_amount") String transferAmount, @RequestParam("motif") String motif){
			//Get Currrent COMPTE COURANT BALANCE:
					
			//long transferFrom= Long.parseLong(transferFromRib); //bech el compte courantId li fel parametrz nraja3ha long
			//long transferTo= Long.parseLong(transferToRib);
					
					int transferMontant = Integer.parseInt(transferAmount);					
					float currentSoldeofCompteCourantTransferingFrom = compteCourantRep.getSoldeCompteCourant(transferFromRib);
					float currentSoldeofCompteEpargneTransferingTo = compteEpargneRep.getSoldeCompteEpargne(transferToRib);
								
			
	//check if transfering into the same compteCourant
					if(transferFromRib.equals(transferToRib)) {

				transactionRep.ajouterTransaction(transferFromRib,transferToRib, "Virement_Immédiat", transferMontant, motif, "Echec du virement immédiat", "envoi au meme compte", currentDateTime);  
				long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(transferFromRib);
				reclamationService.ajouterRec(transferFromRib, "Virement_Immédiat", transferMontant,motif, "Echec du virement immédiat", "envoi au meme compte", currentDateTime);				
				return "cannot transfer to the same Account"; }
			
	//check if transfer amount =0
			if(transferMontant == 0) {

				transactionRep.ajouterTransaction(transferFromRib,transferToRib, "Virement_Immédiat", transferMontant,motif, "Echec du virement immédiat", "Montant de transfer egale à 0", currentDateTime);  
				long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(transferFromRib);
				reclamationService.ajouterRec(transferFromRib, "Virement_Immédiat", transferMontant,motif,"Echec du virement immédiat","Montant de transfer egale à 0", currentDateTime);
				return "transfer amount value =0 , please enter a value greater than 0";
			}
			//check if transfer amount >currentsolde
			if(transferMontant > currentSoldeofCompteCourantTransferingFrom ) {
				
				transactionRep.ajouterTransaction(transferFromRib,transferToRib, "Virement_Immédiat", transferMontant,motif, "Echec du virement immédiat", "Montant Transferé est superieur au solde", currentDateTime);  				
				long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(transferFromRib);
				reclamationService.ajouterRec(transferFromRib, "Virement_Immédiat", transferMontant,motif, "Echec du virement immédiat","Montant Transferé est superieur au solde", currentDateTime);			
				return "impossible de transferer un montant superieur à celui dans votre compte";		
				}
			
			if(transferMontant>=15000) {
				
				transactionRep.ajouterTransaction(transferFromRib,transferToRib, "Virement_Immédiat", transferMontant,motif, "Echec du virement immédiat", "15 000 dinars maximum par virement", currentDateTime);  
				long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(transferFromRib);
				reclamationService.ajouterRec(transferFromRib, "Virement_Immédiat", transferMontant,motif,"Echec du virement immédiat","15 000 dinars maximum par virement", currentDateTime);
				return "15 000 dinars maximum par virement";
				
						}

			
			
	//SINON: update solde
			float newSoldetransferFrom = currentSoldeofCompteCourantTransferingFrom - transferMontant;
			float newSoldetransferTo = currentSoldeofCompteEpargneTransferingTo + transferMontant;
			
			//update comptecourant
			compteCourantRep.ChangeSoldeCompteCourantByRib(newSoldetransferFrom, transferFromRib);
			compteEpargneRep.ChangeSoldeCompteEpargneByRib(newSoldetransferTo, transferToRib);
			transactionRep.ajouterTransaction(transferFromRib,transferToRib, "Virement_Immédiat", transferMontant,motif, "virement immédiat effectué avec succès", transferMontant +" Dinars Transféré", currentDateTime );  
			
			
			return "montant transferé avec succés";
		}
		
		
		//VIREMENT IMMEDIAT
		@PostMapping("/transferFromCompteEpargneToCompteCourant")
		public String transferFromCompteEpargneToCompteCourant (@RequestParam("transferFromRib") String transferFromRib,  @RequestParam("transferToRib") String transferToRib,@RequestParam("transfer_amount") String transferAmount, @RequestParam("motif") String motif){
			//Get Currrent COMPTE COURANT BALANCE:
					
			//long transferFrom= Long.parseLong(transferFromRib); //bech el compte courantId li fel parametrz nraja3ha long
			//long transferTo= Long.parseLong(transferToRib);
					
					int transferMontant = Integer.parseInt(transferAmount);					
					float currentSoldeofCompteEpargneTransferingFrom = compteEpargneRep.getSoldeCompteEpargne(transferFromRib);
					float currentSoldeofCompteCourantTransferingTo = compteCourantRep.getSoldeCompteCourant(transferToRib);
								
			
	//check if transfering into the same compteCourant
					if(transferFromRib.equals(transferToRib)) {

				transactionRep.ajouterTransaction(transferFromRib,transferToRib, "Virement_Immédiat", transferMontant, motif, "Echec du virement immédiat", "envoi au meme compte", currentDateTime);  
				long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(transferFromRib);
				reclamationService.ajouterRec(transferFromRib, "Virement_Immédiat", transferMontant,motif, "Echec du virement immédiat", "envoi au meme compte", currentDateTime);				
				return "cannot transfer to the same Account"; }
			
	//check if transfer amount =0
			if(transferMontant == 0) {
				
				transactionRep.ajouterTransaction(transferFromRib,transferToRib, "Virement_Immédiat", transferMontant,motif, "Echec du virement immédiat", "Montant de transfer egale à 0", currentDateTime);  
				long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(transferFromRib);
				reclamationService.ajouterRec(transferFromRib, "Virement_Immédiat", transferMontant,motif,"Echec du virement immédiat","Montant de transfer egale à 0", currentDateTime);
				return "transfer amount value =0 , please enter a value greater than 0";
			}
			//check if transfer amount >currentsolde
			if(transferMontant > currentSoldeofCompteEpargneTransferingFrom ) {
				
				transactionRep.ajouterTransaction(transferFromRib,transferToRib, "Virement_Immédiat", transferMontant,motif, "Echec du virement immédiat", "Montant Transferé est superieur au solde", currentDateTime);  				
				long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(transferFromRib);
				reclamationService.ajouterRec(transferFromRib, "Virement_Immédiat", transferMontant,motif, "Echec du virement immédiat","Montant Transferé est superieur au solde", currentDateTime);
				return "impossible de transferer un montant superieur à celui dans votre compte";		
				}
			
			if(transferMontant>=15000) {
				
				transactionRep.ajouterTransaction(transferFromRib,transferToRib, "Virement_Immédiat", transferMontant,motif, "Echec du virement immédiat", "15 000 dinars maximum par virement", currentDateTime);  
				long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(transferFromRib);
				reclamationService.ajouterRec(transferFromRib, "Virement_Immédiat", transferMontant,motif,"Echec du virement immédiat","15 000 dinars maximum par virement", currentDateTime);
				return "15 000 dinars maximum par virement";
				
						}			
			
	//SINON: update solde
			float newSoldetransferFrom = currentSoldeofCompteEpargneTransferingFrom - transferMontant;
			float newSoldetransferTo = currentSoldeofCompteCourantTransferingTo + transferMontant;
			
			//update comptecourant
			compteEpargneRep.ChangeSoldeCompteEpargneByRib(newSoldetransferFrom, transferFromRib);
			compteCourantRep.ChangeSoldeCompteCourantByRib(newSoldetransferTo, transferToRib);			
			transactionRep.ajouterTransaction(transferFromRib,transferToRib, "Virement_Immédiat", transferMontant,motif, "virement immédiat effectué avec succès", transferMontant +" Dinars Transféré", currentDateTime );  
									
			return "montant transferé avec succés";
		}
		
	
	
	@PostMapping("/retrait")
	public String retrait (@RequestParam("retrait_amount") String retraitAmount, @RequestParam("monRib") String monRib) throws MailjetException{
		//convert variables
		long rib= Long.parseLong(monRib); //bech el compte courantId li fel parametrz nraja3ha long
		float retraitMontantValue = Float.parseFloat(retraitAmount);
		float currentSolde = compteCourantRep.getSoldeCompteCourant(monRib);
		
		//check if retrait amount =0
		if(retraitMontantValue == 0) {

			transactionRep.ajouterTransaction(monRib,monRib, "Retrait", retraitMontantValue, "---", "retrait sans succés", "montant retiré = "+ retraitMontantValue, currentDateTime);  

			long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(monRib);
			reclamationService.ajouterRec(monRib, "Retrait", retraitMontantValue,"---", "retrait sans succés", "montant retiré = "+ retraitMontantValue, currentDateTime);

			return "montant retiré =0";
		}
		
		//check if solde<= -1200
		if(retraitMontantValue >=1 ) {
			float newSolde = currentSolde - retraitMontantValue;
					
			if(newSolde<= -1200)
			{
					
			transactionRep.ajouterTransaction(monRib,monRib, "Retrait", retraitMontantValue, "---", "retrait sans succés", "Solde insuffisant", currentDateTime);  
					
			long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(monRib);
			reclamationService.ajouterRec(monRib, "Retrait", retraitMontantValue,"---", "retrait sans succés", "Solde insuffisant", currentDateTime);

			return "Solde insuffisant";
					
			}
		}
		
		
		//Découvert
		//CALCUL AGIOS
		if(retraitMontantValue >=1 && (currentSolde<0 && currentSolde>-1200)) //NEKHOU ALIH INTERET
		{
					
			float currentSoldeàdecouvert = compteCourantRep.getSoldeCompteCourant(monRib);
			float montantdudecouvert= 0- currentSoldeàdecouvert; 
			float  tauxAnnuelEffectifGlobal=0.12f; //12%
			//LocalDateTime dateDebutDuDecouvert = transactionService.getDateDebutDuDecouvert(courant_acc,currentSolde);
			
			long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(monRib);
			LocalDateTime dateDebutDecouvert=transactionRep.getDateDebutDuDecouvert(transactionId);
			long duréedudecouvert = ChronoUnit.DAYS.between(dateDebutDecouvert,currentDateTime);//currentdatetime - ekher localdatetime 3mal fiha transaction
			//localdatetime(idcomptecourant, depot) hedhi asa7 melli ta7tha
					
			int nombredejoursdanslannée=365 ; 
				
			//AGIOS = (montant du découvert x durée du découvert x taux de la banque)/nombre de jours dans l’année
			float AGIOS = ((montantdudecouvert * duréedudecouvert * tauxAnnuelEffectifGlobal)/ nombredejoursdanslannée) ;
			float newSoldeApresDecouvert = currentSolde - retraitMontantValue-AGIOS;
					
			compteCourantRep.ChangeSoldeCompteCourantByRib(newSoldeApresDecouvert, monRib);
			transactionRep.ajouterTransaction(monRib,monRib, "Retrait", retraitMontantValue,"---", "retrait avec succés", "durée du decouvert "+duréedudecouvert, currentDateTime);  
					
			return AGIOS +"/" ;
		}	
		
//ELSE		
		//update comptecourant
		float newSolde = currentSolde - retraitMontantValue;
		compteCourantRep.ChangeSoldeCompteCourantByRib(newSolde, monRib);

		transactionRep.ajouterTransaction(monRib,monRib, "Retrait", retraitMontantValue,"---", "retrait avec succés", "montant retiré = "+ retraitMontantValue, currentDateTime);  
		Ms.envoyerMailPersonnelDateExamen();

		return "retrait avec succés";	
		
	}
	
	@PostMapping("/retraitEpargne")
	public String retraitEpargne (@RequestParam("retrait_amount") String retraitAmount, @RequestParam("monRib") String monRib) throws MailjetException{
		
		//convert variables
		long rib= Long.parseLong(monRib); //bech el compte courantId li fel parametrz nraja3ha long
		float retraitMontantValue = Float.parseFloat(retraitAmount);
		float currentSolde = compteEpargneRep.getSoldeCompteEpargne(monRib);
		
		//check if retrait amount =0
		if(retraitMontantValue == 0) {

			transactionRep.ajouterTransaction(monRib,monRib, "Retrait", retraitMontantValue, "---", "retrait sans succés", "montant retiré = "+ retraitMontantValue, currentDateTime);  

			long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(monRib);
			reclamationService.ajouterRec(monRib, "Retrait", retraitMontantValue,"---", "retrait sans succés", "montant retiré = "+ retraitMontantValue, currentDateTime);

			return "montant retiré =0";
		}
		
		//check if solde<= -1200
		if(retraitMontantValue >=1 ) {
			float newSolde = currentSolde - retraitMontantValue;
					
			if(newSolde<= -1200)
			{
					
			transactionRep.ajouterTransaction(monRib,monRib, "Retrait", retraitMontantValue, "---", "retrait sans succés", "Solde insuffisant", currentDateTime);  
					
			long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(monRib);
			reclamationService.ajouterRec(monRib, "Retrait", retraitMontantValue,"---", "retrait sans succés", "Solde insuffisant", currentDateTime);

			return "Solde insuffisant";
					
			}
		}
		
		
		//Découvert
		//CALCUL AGIOS
		if(retraitMontantValue >=1 && (currentSolde<0 && currentSolde>-1200)) //NEKHOU ALIH INTERET
		{
					
			float currentSoldeàdecouvert = compteEpargneRep.getSoldeCompteEpargne(monRib);
			float montantdudecouvert= 0- currentSoldeàdecouvert; 
			float  tauxAnnuelEffectifGlobal=0.12f; //12%
			//LocalDateTime dateDebutDuDecouvert = transactionService.getDateDebutDuDecouvert(courant_acc,currentSolde);
			
			long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(monRib);
			LocalDateTime dateDebutDecouvert=transactionRep.getDateDebutDuDecouvert(transactionId);
			long duréedudecouvert = ChronoUnit.DAYS.between(dateDebutDecouvert,currentDateTime);//currentdatetime - ekher localdatetime 3mal fiha transaction
			//localdatetime(idcomptecourant, depot) hedhi asa7 melli ta7tha
					
			int nombredejoursdanslannée=365 ; 
				
			//AGIOS = (montant du découvert x durée du découvert x taux de la banque)/nombre de jours dans l’année
			float AGIOS = ((montantdudecouvert * duréedudecouvert * tauxAnnuelEffectifGlobal)/ nombredejoursdanslannée) ;
			float newSoldeApresDecouvert = currentSolde - retraitMontantValue-AGIOS;
					
			compteEpargneRep.ChangeSoldeCompteEpargneByRib(currentSolde-AGIOS+retraitMontantValue, monRib);
			transactionRep.ajouterTransaction(monRib,monRib, "Retrait", retraitMontantValue,"---", "Retrait avec succés", "durée du decouvert "+duréedudecouvert, currentDateTime);  
					
			return "AGIOS "+AGIOS +" durée du decouvert: " +duréedudecouvert +" jours" ;
		}	
		
//ELSE		
		//update comptecourant
		float newSolde = currentSolde - retraitMontantValue;
		compteEpargneRep.ChangeSoldeCompteEpargneByRib(newSolde, monRib);

		transactionRep.ajouterTransaction(monRib,monRib, "Retrait", retraitMontantValue,"---", "retrait avec succés", "montant retiré = "+ retraitMontantValue, currentDateTime);  
		Ms.envoyerMailPersonnelDateExamen();

		return "retrait avec succés";	
		
	}
	
	
	
	@PostMapping("/payement")
	public String payement (
			@RequestParam("beneficiaire") String beneficiaire,
			@RequestParam("beneficiaire_rib") String beneficiaire_rib,
			@RequestParam("monRib") String monRib,
			@RequestParam("motif") String motif,
			@RequestParam("montant") String  montantpayement){
		
		//Convert Variables:
		float montantAPayer = Float.parseFloat(montantpayement);
		float currentSolde = compteCourantRep.getSoldeCompteCourant(monRib);
		
		
		//check if payement amount =0
		if(montantAPayer == 0)
		{
		payementRep.ajouterPayement(monRib,beneficiaire,beneficiaire_rib,montantAPayer, motif,"Echec de Paiement","le montant que vous essayez de payer = "+ montantAPayer, currentDateTime);
		//transactionRep.ajouterTransaction(rib, "Paiement", montantAPayer,motif, "Echec de Paiement", "montant payé = "+ montantAPayer, currentDateTime,currentSolde);  
		
		long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(monRib);
		reclamationService.ajouterRec(monRib,"Paiement",montantAPayer,motif,"Echec de Payement", "le montant que vous essayez de payer = "+ montantAPayer, currentDateTime);
		
		return "Montant à payer = 0";
		}
										
		//check if payment amount is more than current balance
		if(montantAPayer > currentSolde) {
			//payementRep.ajouterPayement(rib,beneficiaire,beneficiaire_rib,montantAPayer, motif,"Echec de Payement","le montant que vous essayez de payer = "+ montantAPayer +" est superieur a votre solde", currentDateTime);
			transactionRep.ajouterTransaction(monRib,beneficiaire_rib, "Paiement", montantAPayer,motif, "Echec de Paiement", "le montant que vous essayez de payer = "+ montantAPayer+" est superieur a votre solde", currentDateTime);  

			//ONETOONE 3AL TRANSACTIONID
			long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(monRib);
			reclamationService.ajouterRec(monRib,"Paiement",montantAPayer,motif,"Echec de Paiement", "montant à payer = "+ montantAPayer +" est superieur a votre solde", currentDateTime);

			
		return "montant à payer ne peut pas etre superieur à votre solde";
		}
		
		//update solde 
		float newSolde = currentSolde - montantAPayer;
		compteCourantRep.ChangeSoldeCompteCourantByRib(newSolde,monRib);
		String codeRaison="Payement effectué avec succés";
		
		//Make Payement
		payementRep.ajouterPayement(monRib,beneficiaire,beneficiaire_rib,montantAPayer, motif,"Paiement avec Succés","Paiement Effectué Avec Succés", currentDateTime);
		transactionRep.ajouterTransaction(monRib,beneficiaire_rib, "Paiement", montantAPayer,motif, "Paiement avec Succés", montantAPayer+ " Payés Avec Succés", currentDateTime);  

	return "Payement Effectué Avec Succés";
	}
	
		
		
		@PostMapping("/payementEpargne")
		public String payementEpargne (
				@RequestParam("beneficiaire") String beneficiaire,
				@RequestParam("beneficiaire_rib") String beneficiaire_rib,
				@RequestParam("monRib") String monRib,
				@RequestParam("motif") String motif,
				@RequestParam("montant") String  montantpayement){
			
			//Convert Variables:
			long rib = Long.parseLong(monRib);
			long ribbeneficiaire=Long.parseLong(beneficiaire_rib);
			float montantAPayer = Float.parseFloat(montantpayement);
			float currentSolde = compteEpargneRep.getSoldeCompteEpargne(monRib);
			
			
			//check if payement amount =0
			if(montantAPayer == 0)
			{
			payementRep.ajouterPayement(monRib,beneficiaire,beneficiaire_rib,montantAPayer, motif,"Echec de Paiement","le montant que vous essayez de payer = "+ montantAPayer, currentDateTime);
			//transactionRep.ajouterTransaction(rib, "Paiement", montantAPayer,motif, "Echec de Paiement", "montant payé = "+ montantAPayer, currentDateTime,currentSolde);  
			
			long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(monRib);
			reclamationService.ajouterRec(monRib,"Paiement",montantAPayer,motif,"Echec de Payement", "le montant que vous essayez de payer = "+ montantAPayer, currentDateTime);
			
			return "Montant à payer = 0";
			}
											
			//check if payment amount is more than current balance
			if(montantAPayer > currentSolde) {
				//payementRep.ajouterPayement(rib,beneficiaire,beneficiaire_rib,montantAPayer, motif,"Echec de Payement","le montant que vous essayez de payer = "+ montantAPayer +" est superieur a votre solde", currentDateTime);
				transactionRep.ajouterTransaction(monRib,beneficiaire_rib, "Paiement", montantAPayer,motif, "Echec de Paiement", "le montant que vous essayez de payer = "+ montantAPayer+" est superieur a votre solde", currentDateTime);  

				//ONETOONE 3AL TRANSACTIONID
				long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(monRib);
				reclamationService.ajouterRec(monRib,"Paiement",montantAPayer,motif,"Echec de Paiement", "montant à payer = "+ montantAPayer +" est superieur a votre solde", currentDateTime);

				
			return "montant à payer ne peut pas etre superieur à votre solde";
			}
			
			//update solde 
			float newSolde = currentSolde - montantAPayer;
			compteEpargneRep.ChangeSoldeCompteEpargneByRib(newSolde,monRib);
			String codeRaison="Payement effectué avec succés";
			
			//Make Payement
			payementRep.ajouterPayement(monRib,beneficiaire,beneficiaire_rib,montantAPayer, motif,"Paiement avec Succés","Paiement Effectué Avec Succés", currentDateTime);
			transactionRep.ajouterTransaction(monRib,beneficiaire_rib, "Paiement", montantAPayer,motif, "Paiement avec Succés", montantAPayer+ " Payés Avec Succés", currentDateTime);  

		return "Payement Effectué Avec Succés";
		}
		
		
		//VIREMENT DIFFERE
		@PostMapping("/scheduleVirementDiffere")
		public ResponseEntity<Void> schedule(@RequestBody JobData data)
		{
			
			globalRestService.schedule(data);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
		
		
		
		//VIREMENT PERMANENT
			@PostMapping("/scheduleVirementpermanent")
			public ResponseEntity<Void> scheduleVirementPermanent(@RequestBody JobDataVirementPermanent data)
			{
				
				globalRestServiceVirementPermanent.schedule(data);
				return ResponseEntity.status(HttpStatus.OK).body(null);
			}
			
		
			//VIREMENT DIFFERE
			@PostMapping("/scheduleVirementDiffereEpargne")
			public ResponseEntity<Void> scheduleEpargne(@RequestBody JobData data)
			{
				
				globalRestServiceEpargne.schedule(data);
				return ResponseEntity.status(HttpStatus.OK).body(null);
			}
			
			
			
			//VIREMENT PERMANENT
				@PostMapping("/scheduleVirementpermanentEpargne")
				public ResponseEntity<Void> scheduleVirementPermanentEpargne(@RequestBody JobDataVirementPermanent data)
				{
					
					globalRestServiceVirementPermanentEpargne.schedule(data);
					return ResponseEntity.status(HttpStatus.OK).body(null);
				}	
	
		
				//lel virement
    @GetMapping("/GetNomClientParRib/{rib}")
	public String nameOfUserByRib(@PathVariable("rib") String rib)
	{
					
		return transactionService.nameOfUserByRib(rib);
					
	}
    
    @GetMapping("/GetNomClientParRibEpargne/{rib}")
	public String nameOfUserByRibEpargne(@PathVariable("rib") String rib)
	{
					
		return transactionService.nameOfUserByRibEpargne(rib);
	}
					
	
		
				
//				float AGIOS = ((montantdudecouvert * duréedudecouvert * 0,12%)/ 365) ;		
		@GetMapping("/simulateurAgios")
		public float SimulateurAgios(@RequestParam("montantdudecouvert") String montantdudecouvert,@RequestParam("dureedudecouvert") String dureedudecouvert)	{
			
			float montantdudecouvertt= Float.parseFloat(montantdudecouvert); //bech el compte courantId li fel parametrz nraja3ha long
			long duréedudecouvertt = Long.parseLong(dureedudecouvert);
			
			int nombredejoursdanslannée=365 ; float  tauxannueleffectifglobal=(float)0.12;

			//long idagios= transactionRep.getidAgios();

			float totalagios=((montantdudecouvertt * duréedudecouvertt * 0.12f)/ 365) ;	
			
			transactionRep.ajouterAgios(montantdudecouvertt, duréedudecouvertt,tauxannueleffectifglobal,nombredejoursdanslannée,totalagios);

			
			return totalagios;
		}
		
		//FLASK
		@GetMapping("/payementcheque")
		public String payementcheque (@RequestParam("montant") String  montant,@RequestParam("image") String image ){

			RestTemplate restTemplate = new RestTemplate();
			String resmontant = restTemplate.getForObject("http://af25-34-126-170-30.ngrok.io/"+montant+"/"+image , String.class);

			System.out.println(resmontant);
		return  resmontant;	
			
		}
		
	

	
	

}
