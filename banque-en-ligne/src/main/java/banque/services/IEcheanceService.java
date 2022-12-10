package banque.services;

import java.util.List;

import banque.entities.Echeance;

public interface IEcheanceService {
	
	public List<Echeance> listAll();
	public double calculMensualite(double capital, int mois, double interest);
	public double calculMontantInterest(double capital, double montantTotal);
	public String simulationInterest(double capital, int montant, double interest);
	public Echeance addDetailLoan(Echeance s,Long IdL);
	
}
