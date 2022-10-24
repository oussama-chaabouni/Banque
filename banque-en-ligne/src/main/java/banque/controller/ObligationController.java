package banque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import banque.services.IObligationService;
import banque.entities.Obligation;
@RestController
@RequestMapping("/obligation")
@CrossOrigin(origins = "*")
public class ObligationController {
	@Autowired
	IObligationService ObligationService;

	@PostMapping("/obligationSimulation")
	  @ResponseBody
	  public String ObligationSimulation(@RequestParam float prixOblig,@RequestParam int maturite,@RequestParam float coupon
			  ,@RequestParam double interet, @RequestParam String freqPaiement , @RequestParam String freqCoupon) {
	  return ObligationService.simulationReturn(prixOblig,maturite,coupon,interet,freqPaiement, freqCoupon );
	  }
	
	@RequestMapping(value="/add-obligation/{titre-id}", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public Obligation addSlice(@RequestBody Obligation s,@PathVariable("titre-id") Long IdC)
	{
		Obligation obligation = ObligationService.addObligation(s,IdC);
	return obligation;
	}
	
}
