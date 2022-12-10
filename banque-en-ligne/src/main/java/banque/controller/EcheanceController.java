package banque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import banque.entities.Echeance;
import banque.services.IEcheanceService;

@RestController
@RequestMapping("/echeance")
@CrossOrigin(origins = "*")
public class EcheanceController {
	
	@Autowired
	IEcheanceService echeanceService;
	
	@GetMapping("/retrieve-all-echeances")
	@ResponseBody
	public List<Echeance> getLoans() {
	return echeanceService.listAll();

	}
	
	
	@GetMapping("/creditSimulation")
	  @ResponseBody
	  public String creditPaymentSimulation(@RequestParam double capital,@RequestParam int mois,@RequestParam double interest ) {
	  return echeanceService.simulationInterest(capital,mois,interest);
	  }
	

	@RequestMapping(value="/tableauamortissement/{loan-id}", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public Echeance addDetailLoanLists(@RequestBody Echeance s,@PathVariable("loan-id") Long IdC)
	{
		Echeance echeance = echeanceService.addDetailLoan(s, IdC);
	return echeance;
	}

}
