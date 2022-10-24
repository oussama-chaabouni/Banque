package banque.services;

import banque.entities.Obligation;

public interface IObligationService {
	public String simulationReturn(float prixOblig, int maturite, float coupon, double interet,String freqPaiement , String freqCoupon);
	public Obligation addObligation(Obligation o,Long idt);
	Obligation retrieveObligation(long id);	
}
