package banque.services;

import java.util.List;

import banque.entities.CompteTitre;
import banque.entities.Obligation;

public interface IObligationService {
	public String simulationReturn(float prixOblig, int maturite, float coupon, double interet,String freqPaiement , String freqCoupon);
	public Obligation addObligation(Obligation o,Long idt);
	Obligation retrieveObligation(long id);	
	List<Obligation> retrieveAllObligations();
	public Obligation buyObligation(long id,Long idt);
	List<CompteTitre> retrieveCompteTitre(long id);
}
