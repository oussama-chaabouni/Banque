package banque.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

import banque.entities.CompteTitre;
import banque.entities.Obligation;
import banque.repositories.ObligationRepository;
import banque.repositories.CompteTitreRepository;
@Service
public class ObligationServiceImpl implements IObligationService {
	@Autowired
	ObligationRepository ObligationRepository;
	@Autowired
	CompteTitreRepository CompteTitreRepository;
	
	@Override
	public String simulationReturn(float prixOblig, int maturite, float coupon, double interet, String freqPaiement,String freqCoupon) {
		switch(freqCoupon) {
		case "mensuel":
			coupon = coupon / 12;
			break;
		case "semiannuel":
			coupon = coupon/2;
			break;
		case "trimestriel":	
			coupon = coupon/4;
			break;
		case "annuel":	
			break;
		default:
			coupon = 0;
		}
		switch(freqPaiement) {
		case "mensuel":
			//interet = (float) Math.pow((1+interet),(float) 1/12) - (float)1.0;
			interet=interet/12;
			maturite = maturite * 12 ;
			break;
		case "semiannuel":
			interet = interet/2;
			maturite = maturite * 2 ;
			break;
		case "trimestriel":	
			interet = interet/4;
			maturite = maturite * 4 ;
			break;
		case "annuel":	
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

	@Override
	public Obligation addObligation(Obligation o, Long idt) {
		o.setStatus(false);
		ObligationRepository.save(o);
		
		return o;
	}
	

	@Override
	public Obligation retrieveObligation(long id) {
		return ObligationRepository.findById(id).orElse(null);
	}

	@Override
	public List<Obligation> retrieveAllObligations() {
		List<Obligation> Obligation = (List<Obligation>) ObligationRepository.findAll();
		return Obligation;
	}

	@Override
	public Obligation buyObligation(long id, Long idt) {
		CompteTitre t=CompteTitreRepository.findById(idt).get();
		Obligation o = ObligationRepository.findById(id).orElse(null);
		if ( t.getSolde().compareTo(BigDecimal.valueOf(o.getValeurNominal())) >0) { 
			
			t.setSolde( t.getSolde().subtract(BigDecimal.valueOf(o.getValeurNominal()) ));
			o.setTitreObligations(CompteTitreRepository.findById(idt).orElse(null));
			o.setStatus(true);
			ObligationRepository.save(o);
		}else throw new UnsupportedOperationException("Le mantant du portefeuille n' est pas suffisant");
		
		
		return o;
	}
	
	@Override
	public List <CompteTitre> retrieveCompteTitre(long id) {
		List<CompteTitre> c = (List<CompteTitre>) CompteTitreRepository.findAll();
		return c;
	}
}
