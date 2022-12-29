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

import banque.entities.Payement;
import banque.services.PayementService;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/payement")
public class PayementController {
	
	@Autowired
	PayementService payementService;

	// http://localhost:8082/banque-en-ligne/payement/retrieve-all-payements
		@GetMapping("/retrieve-all-payements")
		@ResponseBody
		public List<Payement> getPayements() {
		List<Payement> listPayements = payementService.retrieveAllPayements();
		return listPayements;
		}
		
		// http://localhost:8082/banque-en-ligne/payement/retrieve-payement/8
		@GetMapping("/retrieve-payement/{payement-id}")
		@ResponseBody
		public Payement retrievePayement(@PathVariable("payement-id") Long payementId) {
		return payementService.retrievePayement(payementId);
		}
		
		// http://localhost:8082/banque-en-ligne/payement/add-payement
		@PostMapping("/add-payement")
		@ResponseBody
		public Payement addPayement(@RequestBody Payement t)
		{
		Payement payement = payementService.addPayement(t);
		return payement;
		}
		
		
		// http://localhost:8082/banque-en-ligne/payement/remove-payement/{payement-id}
		@DeleteMapping("/remove-payement/{payement-id}")
		@ResponseBody
		public void removePayement(@PathVariable("payement-id") Long payementId) {
		payementService.deletePayement(payementId);
		}
		
		// http://localhost:8082/banque-en-ligne/payement/modify-payement
		@PutMapping("/modify-payement")
		@ResponseBody
		public Payement modifyPayement(@RequestBody Payement payement) {
		return payementService.updatePayement(payement);
		}	
		
		
}
