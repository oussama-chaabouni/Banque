package banque.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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

import banque.entities.Transaction;
import banque.entities.TypeTransaction;
import banque.repositories.CompteCourantRepository;
import banque.repositories.PayementRepository;
import banque.repositories.TransactionRepository;
import banque.services.TransactionService;


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
	
	
	// http://localhost:8082/banque-en-ligne/transaction/remove-transaction/{transaction-id}
	@DeleteMapping("/remove-transaction/{transaction-id}")
	@ResponseBody
	public void removeTransaction(@PathVariable("transaction-id") Long transactionId) {
	transactionService.deleteTransaction(transactionId);
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
	@PutMapping("/modify-transactionJPQL/{typeTransaction}/{compteCourantId}")
	@ResponseBody
	public int updateTypeTrBycompteCourantIdJPQL(@PathVariable("typeTransaction") TypeTransaction typeTransaction ,@PathVariable("compteCourantId") long compteCourantId) {
		return transactionService.updateTypeTransactBycompteCourantId(typeTransaction ,compteCourantId);
	}
	
	// http://localhost:8082/banque-en-ligne/transaction/delete-transactionJPQL/ / 
		@DeleteMapping("/delete-transactionJPQL/{typeTransaction}/{source}")
		@ResponseBody
	public int deleteTransactByTypeTransactionAndSourceJPQL(@PathVariable("typeTransaction")TypeTransaction typeTransaction,@PathVariable("source")String source) {
		return transactionService.deleteTransactByTypeTransactionAndSource( typeTransaction, source);
	}
	
		//tekhdemch
	// http://localhost:8082/banque-en-ligne/transaction/retrieve-transactionJPQL
	@PostMapping("/insert-transactionJPQL/{compteCourantId}/{typeTransaction}/{montant}/{source}/{statut}/{raison}")
	@ResponseBody
	public void insertTrJPQL(@PathVariable("compteCourantId") long compteCourantId,@PathVariable("typeTransaction") TypeTransaction typeTransaction ,@PathVariable("montant") float montant,@PathVariable("source") String source,@PathVariable("statut") String statut,@PathVariable("raison") String raison) {
		transactionService.insertTransact(compteCourantId, typeTransaction , montant,source,statut, raison, currentDateTime);
			
	}
	
/////MICRO SERVICES
	
	//li fel param mta3 el methode houma li bech ndakhalhom
	@PostMapping("/depot")
	public String depot (@RequestParam("deposit_amount") String depositAmount, @RequestParam("compte-courant_id") String compteCourantId){
		
		//check for empty strings:
		if(depositAmount.isEmpty() || compteCourantId.isEmpty()) {
			return "empty";
		}
		
		//Get Currrent COMPTE COURANT BALANCE:
		long courant_acc= Long.parseLong(compteCourantId); //bech el compte courantId li fel parametrz nraja3ha long
		float depositMontantValue = Float.parseFloat(depositAmount);
		
		//check if deposit amount =0
		if(depositMontantValue == 0) {
			transactionRep.ajouterTransaction(courant_acc, "Dépot", depositMontantValue, "Compte Courant", "dépot sans succés", "montant de dépot = 0", currentDateTime);  
			return "deposit amount value =0";
			
		}
			
		
		//update solde
		float currentSolde = compteCourantRep.getSoldeCompteCourant(courant_acc);
		float newSolde = currentSolde + depositMontantValue;
		//update comptecourant
		compteCourantRep.ChangeSoldeCompteCourantById(newSolde, courant_acc);
		//Save f table transaction
		transactionRep.ajouterTransaction(courant_acc, "Dépot", depositMontantValue, "Compte Courant", "dépot avec succés", "montant de dépot = "+ depositMontantValue, currentDateTime);  

		return "depot avec succés";
	
		
	}
	
	@PostMapping("/transfer")
	public String transfer (@RequestParam("transferFrom") String transferFrom,  @RequestParam("transferTo") String transferTo,@RequestParam("transfer_amount") String transferAmount){
		//Get Currrent COMPTE COURANT BALANCE:
				long transferFromId= Long.parseLong(transferFrom); //bech el compte courantId li fel parametrz nraja3ha long
				long transferToId= Long.parseLong(transferTo);
				
				int transferMontant = Integer.parseInt(transferAmount);
				
				float currentSoldeofCompteCourantTransferingFrom = compteCourantRep.getSoldeCompteCourant(transferFromId);
				float currentSoldeofCompteCourantTransferingTo = compteCourantRep.getSoldeCompteCourant(transferToId);
				
				
		//check for empty strings:
		if(transferFrom.isEmpty() || transferTo.isEmpty() || transferAmount.isEmpty() ) {
			transactionRep.ajouterTransaction(transferFromId, "Transfer", transferMontant, "Compte Courant", "transfer sans succés", "champsvides", currentDateTime);  
			return "empty fields";
		}	
		

		
//check if transfering into the same compteCourant
		if(transferFromId == transferToId) {
			transactionRep.ajouterTransaction(transferFromId, "Transfer", transferMontant, "Compte Courant", "transfer sans succés", "envoi au meme compte", currentDateTime);  
			return "cannot transfer to the same Account"; }
		
//check if transfer amount =0
		if(transferMontant == 0) {
			transactionRep.ajouterTransaction(transferFromId, "Transfer", transferMontant, "Compte Courant", "transfer sans succés", "Montant de transfer egale à 0", currentDateTime);  
			return "transfer amount value =0 , please enter a value greater than 0";
		}
		//check if transfer amount =0
				if(transferMontant > currentSoldeofCompteCourantTransferingFrom) {
			transactionRep.ajouterTransaction(transferFromId, "Transfer", transferMontant, "Compte Courant", "transfer sans succés", "Montant de Transfer est superieur au solde", currentDateTime);  
			return "impossible de transferer un montant superieur à celui dans votre compte";		}
		
//SINON: update solde
		float newSoldetransferFrom = currentSoldeofCompteCourantTransferingFrom - transferMontant;
		float newSoldetransferTo = currentSoldeofCompteCourantTransferingTo + transferMontant;
		
		//update comptecourant
		compteCourantRep.ChangeSoldeCompteCourantById(newSoldetransferFrom, transferFromId);
		compteCourantRep.ChangeSoldeCompteCourantById(newSoldetransferTo, transferToId);
		
		transactionRep.ajouterTransaction(transferFromId, "Transfer", transferMontant, "Compte Courant", "transfer avec succés", transferMontant +" Dinars Transféré", currentDateTime);  
		
		
		
		return "montant transferé avec succés";
	}
	
	
	@PostMapping("/retrait")
	public String retrait (@RequestParam("retrait_amount") String retraitAmount, @RequestParam("compte_courant_id") String compteCourantId){
		
		//check for empty strings:
		if(retraitAmount.isEmpty() || compteCourantId.isEmpty()) {
			return "empty";
		}
		
		//convert variables
		long courant_acc= Long.parseLong(compteCourantId); //bech el compte courantId li fel parametrz nraja3ha long
		float retraitMontantValue = Float.parseFloat(retraitAmount);
		
		//check if deposit amount =0
		if(retraitMontantValue == 0) {
			transactionRep.ajouterTransaction(courant_acc, "Retrait", retraitMontantValue, "Compte Courant", "retrait sans succés", "montant retraité = "+ retraitMontantValue, currentDateTime);  

			return "deposit amount value =0";
		}
		
		//update solde
		float currentSolde = compteCourantRep.getSoldeCompteCourant(courant_acc);
		float newSolde = currentSolde - retraitMontantValue;
		
		//update comptecourant
		compteCourantRep.ChangeSoldeCompteCourantById(newSolde, courant_acc);
//save f table transaction
		transactionRep.ajouterTransaction(courant_acc, "Retrait", retraitMontantValue, "Compte Courant", "retrait avec succés", "montant retraité = "+ retraitMontantValue, currentDateTime);  

		return "depot avec succés";
	
		
	}
	
	@PostMapping("/payement")
	public String payement (
			@RequestParam("beneficiaire") String beneficiaire,
			@RequestParam("beneficiaire_courant_acc_num") String beneficiaire_courant_acc_num,
			@RequestParam("id_de_mon_compte_courant") String idCompteCourant,
			@RequestParam("reference") String reference,
			@RequestParam("montant") String  montantpayement){
		
	//check for empty strings:
		if(beneficiaire.isEmpty() || beneficiaire_courant_acc_num.isEmpty() || idCompteCourant.isEmpty() || reference.isEmpty() || montantpayement.isEmpty() ) {
			return "empty fields";
		}	
		
		//Convert Variables:
		long compteCourantId = Long.parseLong(idCompteCourant);
		float montantAPayer = Float.parseFloat(montantpayement);
		
		//check if payement amount =0
		if(montantAPayer == 0)
		return "Montant à payer = 0";
		
		
		float currentSolde = compteCourantRep.getSoldeCompteCourant(compteCourantId);
				
		//check if payment amount is more than current balance
		if(montantAPayer > currentSolde)
			return "montant à payer ne peut pas etre superieur à ton solde";
		
		//update solde 
		float newSolde = currentSolde - montantAPayer;
		compteCourantRep.ChangeSoldeCompteCourantById(newSolde, compteCourantId);
		String codeRaison="Payement effectué avec succés";
		
		//Make Payement
		payementRep.ajouterPayement(compteCourantId,beneficiaire,beneficiaire_courant_acc_num,montantAPayer, reference,"Succés","Payement Effectué Avec Succés", currentDateTime);
		
	return "Payement Effectué Avec Succés";
	}
	
			

	
	
	
	
	

}
