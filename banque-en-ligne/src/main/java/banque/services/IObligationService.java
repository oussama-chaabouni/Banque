package banque.services;

public interface IObligationService {
	public String simulationReturn(float prixOblig, int maturite, float coupon, double interet,String freqPaiement , String freqCoupon);
}
