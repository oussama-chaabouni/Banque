package banque.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import banque.entities.Credit;
import banque.services.ICreditService;

@RestController
@RequestMapping("/credit")
@CrossOrigin(origins = "*")
public class CreditController {
	@Autowired
	ICreditService creditService;
	
	//###################################################"Basic#############################
	
	// http://localhost:8082/banque-en-ligne/retrieve-all-credits/
	@GetMapping("/retrieve-all-credits")
	@ResponseBody
	public List<Credit> getCredits() {
	List<Credit> listCredit = creditService.retrieveAllCredits();
	return listCredit;
	}	
	
	// http://localhost:8082/banque-en-ligne/credit/add-loan
	@RequestMapping(value="/add-loan/{client-id}", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public Credit addLoan(@RequestBody Credit o,@PathVariable("client-id") Long IdC)
	{
	Credit credit = creditService.addLoanRequest(o,IdC);
	return credit;
	}
	
	
	// http://localhost:8082/banque-en-ligne/credit/retrieve-credit/1
	@GetMapping("/retrieve-credit/{credit-id}")
	@ResponseBody
	public Credit retrieveCredit(@PathVariable("credit-id") Long IDCredit) {
	return creditService.retrieveCredit(IDCredit);
	}
	
	// http://localhost:8082/banque-en-ligne/credit/acceptLoanRequest/1
	@PutMapping("/acceptLoanRequest/{loan-id}")
	@ResponseBody public Credit acceptLoanRequest(@PathVariable("loan-id") Long loanId) {
		return creditService.acceptLoanRequest(loanId);
	}
	
	// http://localhost:8082/banque-en-ligne/credit/denyLoanRequest/1
	@PutMapping("/denyLoanRequest/{loan-id}")
	@ResponseBody public Credit denyLoanRequest(@PathVariable("loan-id") Long loanId) {
		return creditService.denyLoanRequest(loanId);
	}
	
	
	/*/ http://localhost:8082/banque-en-ligne/credit/add-credit
	@PostMapping("/add-credit")
	@ResponseBody
	public Credit addCredit(@RequestBody Credit c)
	{
		Credit credit = creditService.addCredit(c);
		return credit;
	}*/
	
	
	// http://localhost:8082/banque-en-ligne/remove-credit/1
	@DeleteMapping("/remove-credit/{credit-id}")
	@ResponseBody
	public void removeCredit(@PathVariable("credit-id") Long creditId) {
		creditService.removeCredit(creditId);
	}
	
	
	// http://localhost:8082/banque-en-ligne/update-credit
	@PutMapping("/update-credit")
	@ResponseBody
	public Credit updateCredit(@RequestBody Credit credit) {
	return creditService.updateCredit(credit);
	}
	
	@GetMapping("/predict-score/{loan-id}")
	  @ResponseBody
	  public Map<Object, Object>  calculScore(@PathVariable("loan-id") Long loanId) {
		
		HashMap<Object, Object> mapA = (HashMap<Object, Object>) creditService.getScoreAttr(loanId);
		HashMap<Object, Object> mapTransf= new HashMap<>();
		HashMap<Object, Object> mapTot = new HashMap<>();
		System.out.println(mapA);
					
				//mapId.put(i, mapT);
					mapTransf=(HashMap<Object, Object>) creditService.scoreDataTransform(mapA.get("sex").toString(), 
					mapA.get("housing").toString(),
					//1000.0,
					Float.parseFloat(mapA.get("chekingAccount").toString()), 
					mapA.get("purpose").toString());
					mapTot.put("age", Float.parseFloat(mapA.get("age").toString()));
					mapTot.put("sex", mapTransf.get("0"));
					mapTot.put("housing", mapTransf.get("1"));
					mapTot.put("checkingAccount", mapTransf.get("2"));
					mapTot.put("purpose",  mapTransf.get("3"));
					mapTot.put("amount", mapA.get("amount"));
					mapTot.put("mois", mapA.get("mois"));
				
			
			return mapTot;
	}

	@GetMapping("/retrieve-score")
	@ResponseBody

	public Object getScore() {
		ObjectMapper oMapper = new ObjectMapper();
		
		RestTemplate restTemplate = new RestTemplate();
		Object response = restTemplate.getForObject("http://127.0.0.1:5000/predict", Object.class);
		Map<String, Object> map = oMapper.convertValue(response, Map.class);
		return map;
	 
	}
}
