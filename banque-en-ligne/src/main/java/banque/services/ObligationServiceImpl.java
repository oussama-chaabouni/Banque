package banque.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import banque.entities.Obligation;
import banque.repositories.ObligationRepository;
@Service
public class ObligationServiceImpl implements IObligationService {
	@Autowired
	ObligationRepository ObligationRepository;
	
	@Override
	public String simulationReturn(float prixOblig, int maturite, float coupon, double interet, String freqPaiement,String freqCoupon) {
		
		switch(freqPaiement) {
		case "mensuel":
			//interet = (float) Math.pow((1+interet),(float) 1/12) - (float)1.0;
			interet=interet/12;
			maturite = maturite * 12 ;
			coupon = coupon / 12;
			break;
		case "annuel":	

			break;
		case "semiannuel":
			interet = interet/2;
			maturite = maturite * 2 ;
			coupon = coupon / 2;
			break;
		case "trimestriel":	
			interet = interet/4;
			maturite = maturite * 4 ;
			coupon = coupon / 4;
			break;
		default:
			interet = 0;
		}
		float actu=0;
		float rendement = (float) (prixOblig * coupon * ((1- (Math.pow((1+ interet),-maturite))) /interet )) 
				
				+ (float) (prixOblig * Math.pow((float)(1+interet),-maturite));
		
		for (int i = 1; i < maturite; i++) {
		
		 actu =(float) (  i* prixOblig * coupon * (Math.pow((1+ interet),-i)))  + actu  ;
		 
		}
		actu = actu +  (float) (  maturite * prixOblig * (coupon+1) * (Math.pow((1+ interet),-maturite)));
		float duration = actu / rendement;
		
		float sensibilite = (float) (duration / -(1+ interet));
		
		
		return "Rendement: "+ rendement  +"\n "+ "Duration: " + duration +"\n "+ "Sensibilite: " + sensibilite ;
	}
}
