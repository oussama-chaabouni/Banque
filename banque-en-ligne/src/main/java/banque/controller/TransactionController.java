package banque.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

import banque.entities.Transaction;
import banque.entities.TypeTransaction;
import banque.repositories.CompteCourantRepository;
import banque.repositories.PayementRepository;
import banque.repositories.TransactionRepository;
import banque.services.ReclamationService;
import banque.services.TransactionService;
import banque.services.GlobalRestService;
import banque.services.GlobalRestServiceVirementPermanent;
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
	PayementRepository payementRep;
	
	@Autowired
	ReclamationService reclamationService;
	
	@Autowired
	GlobalRestService globalRestService;
	
	@Autowired
	GlobalRestServiceVirementPermanent globalRestServiceVirementPermanent;
	
	
	
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
	public int updateTypeTrBycompteCourantIdJPQL(@PathVariable("typeTransaction") TypeTransaction typeTransaction ,@PathVariable("rib") long rib) {
		return transactionService.updateTypeTransactBycompteCourantRib(typeTransaction ,rib);
	}
	
	// http://localhost:8082/banque-en-ligne/transaction/delete-transactionJPQL/ / 
		@DeleteMapping("/delete-transactionJPQL/{typeTransaction}/{rib}")
		@ResponseBody
	public int deleteTransactByTypeTransactionAndSourceJPQL(@PathVariable("typeTransaction")TypeTransaction typeTransaction,@PathVariable("ribCompteCourant")long rib) {
		return transactionService.deleteTransactByTypeTransactionAndRibc( typeTransaction,rib);
	}
		
		
///////////NEST7A9HOM ANGULAR		
		
		
		@GetMapping("/getsoldecomptecourant/{rib}")
		@ResponseBody
		public float getSoldeCompteCourant(@PathVariable long rib)
	    {
	        return compteCourantRep.getSoldeCompteCourant(rib) ;
	    }
		
		@PutMapping("/ChangeSoldeCompteCourantByRib/{new_solde}/{rib}")
		@ResponseBody
	    void ChangeSoldeCompteCourantByRib(@PathVariable float new_solde, @PathVariable long rib) {
			compteCourantRep.ChangeSoldeCompteCourantByRib(new_solde, rib);
		}
		
//////////	
	
	
/////MICRO SERVICES
	
	//li fel param mta3 el methode houma li bech ndakhalhom
	@PostMapping("/depot")
	public String depot (@RequestParam("deposit_amount") String depositAmount, @RequestParam("monRib") String monRib){
		
		
		//Get Currrent COMPTE COURANT BALANCE:
		long rib= Long.parseLong(monRib); //bech el compte courantId li fel parametrz nraja3ha long
		float depositMontantValue = Float.parseFloat(depositAmount);
		float currentSolde = compteCourantRep.getSoldeCompteCourant(rib);
						
		//check if deposit amount =0
		if(depositMontantValue == 0) {
			transactionRep.ajouterTransaction(rib, "Dépot", depositMontantValue, "Dépot", "dépot sans succés", "montant de dépot = 0", currentDateTime);  
			
			//transactionId bech najem n3amer colonne transactionid f table reclamation
			long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(rib);
			
			reclamationService.ajouterRec(rib, "Dépot", depositMontantValue,"Dépot", "dépot sans succés", "montant de dépot = 0", currentDateTime);		
			
			return "deposit amount value =0";
			
		}
		
		
		//Découvert
		//CALCUL AGIOS
		if(depositMontantValue >=1 && (currentSolde<0 && currentSolde>-1200)) //NEKHOU ALIH INTERET
		{
			
			float currentSoldeàdecouvert = compteCourantRep.getSoldeCompteCourant(rib);
			float montantdudecouvert= 0- currentSoldeàdecouvert; 
			float  tauxAnnuelEffectifGlobal=0.12f; //12%
			
			long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(rib);
			LocalDateTime dateDebutDecouvert=transactionRep.getDateDebutDuDecouvert(transactionId);
			long duréedudecouvert = ChronoUnit.DAYS.between(dateDebutDecouvert,currentDateTime);//currentdatetime - ekher localdatetime 3mal fiha transaction
			//localdatetime(idcomptecourant, depot) hedhi asa7 melli ta7tha
			
			int nombredejoursdanslannée=365 ; 
		
			//AGIOS = (montant du découvert x durée du découvert x taux de la banque)/nombre de jours dans l’année
			float AGIOS = ((montantdudecouvert * duréedudecouvert * tauxAnnuelEffectifGlobal)/ nombredejoursdanslannée) ;
			float newSoldeApresDecouvert = currentSolde + depositMontantValue-AGIOS;
			
			compteCourantRep.ChangeSoldeCompteCourantByRib(newSoldeApresDecouvert,rib);
			transactionRep.ajouterTransaction(rib, "Dépot", depositMontantValue,"Dépot", "dépot avec succés", "durée du decouvert "+duréedudecouvert, currentDateTime);  
			
			return "AGIOS "+AGIOS +" durée du decouvert" +duréedudecouvert +"jours" ;
		}	
		
		//update comptecourant
		//update solde
		float newSolde = currentSolde + depositMontantValue;
		compteCourantRep.ChangeSoldeCompteCourantByRib(newSolde,rib);
		//Save f table transaction
		transactionRep.ajouterTransaction(rib, "Dépot", depositMontantValue,"Dépot", "dépot avec succés", "montant de dépot = "+ depositMontantValue, currentDateTime);  

		return "depot avec succés";
	
		
	}
	
	//VIREMENT IMMEDIAT
	@PostMapping("/transfer")
	public String transfer (@RequestParam("transferFromRib") String transferFromRib,  @RequestParam("transferToRib") String transferToRib,@RequestParam("transfer_amount") String transferAmount, @RequestParam("motif") String motif){
		//Get Currrent COMPTE COURANT BALANCE:
				long transferFrom= Long.parseLong(transferFromRib); //bech el compte courantId li fel parametrz nraja3ha long
				long transferTo= Long.parseLong(transferToRib);
				
				int transferMontant = Integer.parseInt(transferAmount);
				
				float currentSoldeofCompteCourantTransferingFrom = compteCourantRep.getSoldeCompteCourant(transferFrom);
				float currentSoldeofCompteCourantTransferingTo = compteCourantRep.getSoldeCompteCourant(transferTo);
							
		
//check if transfering into the same compteCourant
		if(transferFrom == transferTo) {

			transactionRep.ajouterTransaction(transferFrom, "Virement_Immédiat", transferMontant, motif, "Echec du virement immédiat", "envoi au meme compte", currentDateTime);  

			long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(transferFrom);
			reclamationService.ajouterRec(transferFrom, "Virement_Immédiat", transferMontant,motif, "Echec du virement immédiat", "envoi au meme compte", currentDateTime);
			
			return "cannot transfer to the same Account"; }
		
//check if transfer amount =0
		if(transferMontant == 0) {

			transactionRep.ajouterTransaction(transferFrom, "Virement_Immédiat", transferMontant,motif, "Echec du virement immédiat", "Montant de transfer egale à 0", currentDateTime);  

			long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(transferFrom);
			reclamationService.ajouterRec(transferFrom, "Virement_Immédiat", transferMontant,motif,"Echec du virement immédiat","Montant de transfer egale à 0", currentDateTime);

			return "transfer amount value =0 , please enter a value greater than 0";
		}
		//check if transfer amount >currentsolde
		if(transferMontant > currentSoldeofCompteCourantTransferingFrom ) {
			
			transactionRep.ajouterTransaction(transferFrom, "Virement_Immédiat", transferMontant,motif, "Echec du virement immédiat", "Montant Transferé est superieur au solde", currentDateTime);  
			
			long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(transferFrom);
			reclamationService.ajouterRec(transferFrom, "Virement_Immédiat", transferMontant,motif, "Echec du virement immédiat","Montant Transferé est superieur au solde", currentDateTime);

			
			return "impossible de transferer un montant superieur à celui dans votre compte";		
			}
		
		if(transferMontant>=15000) {
			
			transactionRep.ajouterTransaction(transferFrom, "Virement_Immédiat", transferMontant,motif, "Echec du virement immédiat", "15 000 dinars maximum par virement", currentDateTime);  

			long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(transferFrom);
			reclamationService.ajouterRec(transferFrom, "Virement_Immédiat", transferMontant,motif,"Echec du virement immédiat","15 000 dinars maximum par virement", currentDateTime);

			return "15 000 dinars maximum par virement";
			
					}

		
		
//SINON: update solde
		float newSoldetransferFrom = currentSoldeofCompteCourantTransferingFrom - transferMontant;
		float newSoldetransferTo = currentSoldeofCompteCourantTransferingTo + transferMontant;
		
		//update comptecourant
		compteCourantRep.ChangeSoldeCompteCourantByRib(newSoldetransferFrom, transferFrom);
		compteCourantRep.ChangeSoldeCompteCourantByRib(newSoldetransferTo, transferTo);
		
		transactionRep.ajouterTransaction(transferFrom, "Virement_Immédiat", transferMontant,motif, "virement immédiat effectué avec succès", transferMontant +" Dinars Transféré", currentDateTime );  
		
		
		
		return "montant transferé avec succés";
	}
	
	
	@PostMapping("/retrait")
	public String retrait (@RequestParam("retrait_amount") String retraitAmount, @RequestParam("monRib") String monRib){
		
		//convert variables
		long rib= Long.parseLong(monRib); //bech el compte courantId li fel parametrz nraja3ha long
		float retraitMontantValue = Float.parseFloat(retraitAmount);
		float currentSolde = compteCourantRep.getSoldeCompteCourant(rib);
		
		//check if retrait amount =0
		if(retraitMontantValue == 0) {

			transactionRep.ajouterTransaction(rib, "Retrait", retraitMontantValue, "", "retrait sans succés", "montant retiré = "+ retraitMontantValue, currentDateTime);  

			long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(rib);
			reclamationService.ajouterRec(rib, "Retrait", retraitMontantValue,"", "retrait sans succés", "montant retiré = "+ retraitMontantValue, currentDateTime);

			return "montant retiré =0";
		}
		
		//check if solde<= -1200
		if(retraitMontantValue >=1 ) {
			float newSolde = currentSolde - retraitMontantValue;
					
			if(newSolde<= -1200)
			{
					
			transactionRep.ajouterTransaction(rib, "Retrait", retraitMontantValue, "", "retrait sans succés", "Solde insuffisant", currentDateTime);  
					
			long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(rib);
			reclamationService.ajouterRec(rib, "Retrait", retraitMontantValue,"", "retrait sans succés", "Solde insuffisant", currentDateTime);

			return "Solde insuffisant";
					
			}
		}
		
		
		//Découvert
		//CALCUL AGIOS
		if(retraitMontantValue >=1 && (currentSolde<0 && currentSolde>-1200)) //NEKHOU ALIH INTERET
		{
					
			float currentSoldeàdecouvert = compteCourantRep.getSoldeCompteCourant(rib);
			float montantdudecouvert= 0- currentSoldeàdecouvert; 
			float  tauxAnnuelEffectifGlobal=0.12f; //12%
			//LocalDateTime dateDebutDuDecouvert = transactionService.getDateDebutDuDecouvert(courant_acc,currentSolde);
			
			long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(rib);
			LocalDateTime dateDebutDecouvert=transactionRep.getDateDebutDuDecouvert(transactionId);
			long duréedudecouvert = ChronoUnit.DAYS.between(dateDebutDecouvert,currentDateTime);//currentdatetime - ekher localdatetime 3mal fiha transaction
			//localdatetime(idcomptecourant, depot) hedhi asa7 melli ta7tha
					
			int nombredejoursdanslannée=365 ; 
				
			//AGIOS = (montant du découvert x durée du découvert x taux de la banque)/nombre de jours dans l’année
			float AGIOS = ((montantdudecouvert * duréedudecouvert * tauxAnnuelEffectifGlobal)/ nombredejoursdanslannée) ;
			float newSoldeApresDecouvert = currentSolde - retraitMontantValue-AGIOS;
					
			compteCourantRep.ChangeSoldeCompteCourantByRib(currentSolde-AGIOS+retraitMontantValue, rib);
			transactionRep.ajouterTransaction(rib, "Retrait", retraitMontantValue,"", "Retrait avec succés", "durée du decouvert "+duréedudecouvert, currentDateTime);  
					
			return "AGIOS "+AGIOS +" durée du decouvert: " +duréedudecouvert +" jours" ;
		}	
		
//ELSE		
		//update comptecourant
		float newSolde = currentSolde - retraitMontantValue;
		compteCourantRep.ChangeSoldeCompteCourantByRib(newSolde, rib);

		transactionRep.ajouterTransaction(rib, "Retrait", retraitMontantValue,"", "retrait avec succés", "montant retiré = "+ retraitMontantValue, currentDateTime);  

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
		long rib = Long.parseLong(monRib);
		float montantAPayer = Float.parseFloat(montantpayement);
		float currentSolde = compteCourantRep.getSoldeCompteCourant(rib);
		
		
		//check if payement amount =0
		if(montantAPayer == 0)
		{
		payementRep.ajouterPayement(rib,beneficiaire,beneficiaire_rib,montantAPayer, motif,"Echec de Paiement","le montant que vous essayez de payer = "+ montantAPayer, currentDateTime);
		//transactionRep.ajouterTransaction(rib, "Paiement", montantAPayer,motif, "Echec de Paiement", "montant payé = "+ montantAPayer, currentDateTime,currentSolde);  
		
		long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(rib);
		reclamationService.ajouterRec(rib,"Payement",montantAPayer,motif,"Echec de Payement", "le montant que vous essayez de payer = "+ montantAPayer, currentDateTime);
		
		return "Montant à payer = 0";
		}
										
		//check if payment amount is more than current balance
		if(montantAPayer > currentSolde) {
			//payementRep.ajouterPayement(rib,beneficiaire,beneficiaire_rib,montantAPayer, motif,"Echec de Payement","le montant que vous essayez de payer = "+ montantAPayer +" est superieur a votre solde", currentDateTime);
			transactionRep.ajouterTransaction(rib, "Paiement", montantAPayer,motif, "Echec de Paiement", "le montant que vous essayez de payer = "+ montantAPayer+" est superieur a votre solde", currentDateTime);  

			//ONETOONE 3AL TRANSACTIONID
			long transactionId = transactionRep.findTopByOrderByIdTransactionDesc(rib);
			reclamationService.ajouterRec(rib,"Payement",montantAPayer,motif,"Echec de Paiement", "montant à payer = "+ montantAPayer +" est superieur a votre solde", currentDateTime);

			
		return "montant à payer ne peut pas etre superieur à votre solde";
		}
		
		//update solde 
		float newSolde = currentSolde - montantAPayer;
		compteCourantRep.ChangeSoldeCompteCourantByRib(newSolde,rib);
		String codeRaison="Payement effectué avec succés";
		
		//Make Payement
		payementRep.ajouterPayement(rib,beneficiaire,beneficiaire_rib,montantAPayer, motif,"Paiement avec Succés","Paiement Effectué Avec Succés", currentDateTime);
		transactionRep.ajouterTransaction(rib, "Paiement", montantAPayer,motif, "Paiement avec Succés", montantAPayer+ " Payés Avec Succés", currentDateTime);  

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
	
	
/*
 * @Autowired
	Scheduler quartzScheduler;
	
	@Autowired
	ScheduledInfoRepo scheduledInfoRepo;
	
	@PostConstruct public void postContruct() { try {quartzScheduler.start(); }
	catch(SchedulerException exception) {
		System.out.println("scheduler throws exception" +exception); } 
	}
	
	//@RequestParam("transferFromRib") String transferFromRib,  @RequestParam("transferToRib") String transferToRib,@RequestParam("transfer_amount") String transferAmount, @RequestParam("motif") String motif
	//VIREMENT DIFFERE
		@PostMapping("/scheduleVirementDifferetest")
		public ResponseEntity<Void> scheduletest(@RequestParam("transferFrom") String transferFrom,  @RequestParam("transferTo") String transferTo,@RequestParam("montant") String montant, @RequestParam("motif") String motif,@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime startTime,JobData data )
		{
			//long transferFromm= Long.parseLong(transferFrom); //bech el compte courantId li fel parametrz nraja3ha long
			//long transferToo= Long.parseLong(transferTo);
			
			float montantt = Integer.parseInt(montant);
			
			String motiff = data.getMotif();
			LocalDateTime startTimee = data.getStartTime();
			//long transferFromm= String.toString(transferFrom); 
			//String transferToo= Long.toString(transferTo);
			
			//int counter = data.getCounter(); //counter = 9addech tet3awed men mara //par ex tetexecuta kol 30j
			//int gapDuration = data.getGapDuration(); //bin counters 9adech testanna bech tetexecuta
			
			ZonedDateTime zonedDateTime =  ZonedDateTime.of(data.getStartTime(),ZoneId.of("Europe/Paris"));
			
			JobDataMap dataMap = new JobDataMap();
			//dataMap.put("test", "this is a test");
			
			
			ScheduledInfo  scheduledInfo = new ScheduledInfo();
			scheduledInfo.setTransferFrom(transferFrom);
			scheduledInfo.setTransferTo(transferTo);
			scheduledInfo.setMontant(montantt);
			scheduledInfo.setMotif(motiff);
			scheduledInfo.setStartTime(startTimee);
			
			 
			
			JobDetail detail = JobBuilder.newJob(ScheduledJob.class)
					.usingJobData(dataMap)
					.storeDurably(false)
					.build();
			
			Trigger trigger = TriggerBuilder.newTrigger()
					.startAt(Date.from(zonedDateTime.toInstant()))
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(1).withRepeatCount(0))
					.build();
			
			/*Trigger tt = TriggerBuilder.newTrigger().withIdentity(fruitName)
					.startAt(Date.from(zonedDateTime.toInstant()))
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(gapDuration).withRepeatCount(24*counter))
					.build();
			*/
			
/*			
			try {
				quartzScheduler.scheduleJob(detail,trigger);
				scheduledInfoRepo.save(scheduledInfo);
				
			} catch (SchedulerException e) {
				
				e.printStackTrace();
			}
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
		
	//VIREMENT PERMANENT
		@PostMapping("/scheduleVirementpermanent")
		public ResponseEntity<Void> scheduleVirementPermanent(@RequestBody JobDataVirementPermanent data)
		{
			
			globalRestServiceVirementPermanent.schedule(data);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}	
 */
			 

	
	
	
	
	

}
