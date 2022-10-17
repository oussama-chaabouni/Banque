package banque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import banque.services.IObligationService;

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
	
	
}
