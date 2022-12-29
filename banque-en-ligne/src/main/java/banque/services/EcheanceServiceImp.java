package banque.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import banque.entities.Credit;
import banque.entities.Echeance;
import banque.repositories.CreditRepository;
import banque.repositories.EcheanceRepository;

@Service
public class EcheanceServiceImp implements IEcheanceService {
	
	@Autowired
	EcheanceRepository echeanceRepository;
	@Autowired
	CreditRepository creditRepository;

	@Override
	public double calculMensualite(double capital, int mois, double interest) {
		double mens=0;
		mens=(capital*(interest/12))/
				(1-Math.pow((1+interest/12),-mois));
		return mens;
	}

	@Override
	public double calculMontantInterest(double capital, double montantTotal) {
		return montantTotal - capital;
	}

	@Override
	public String simulationInterest(double capital, int mois, double interest) {
		double mensualite = calculMensualite(capital, mois , interest) ;
		double total = mensualite * mois;
		double mantantinterest = calculMontantInterest(capital,total);
		
		return "Your loan of :  " +capital +"\n "+ "Monthly Payments :  "+ mensualite +"\n "
		+ "Interest Rate : " +interest +"\n "
				
				+"Total Interest : " +mantantinterest +"\n "+ "Total Payment : " + total ;
	}

	@Override
	public Echeance addDetailLoan(Echeance s, Long IdL) {
		s.setCreditEcheances(creditRepository.findById(IdL).orElse(null));
		
		Credit l=creditRepository.findById(IdL).get();
		
		double MontantRestant;
		double interestM = l.getMontantTotal()*(l.getInteret()/12);
		double amortissement = calculMensualite(l.getMontantTotal(), l.getDureeDuCredit(),l.getInteret()) - interestM;
		MontantRestant=l.getMontantTotal();
		System.out.println(l.getDureeDuCredit());
		for (int i=0;i<l.getDureeDuCredit();i++) {
			
			Echeance sl  = new Echeance ();
			sl.setCreditEcheances(creditRepository.findById(IdL).orElse(null));
			sl.setMensualite((float) calculMensualite(l.getMontantTotal(), l.getDureeDuCredit(),l.getInteret()));
			
			sl.setMantantRestant((float) MontantRestant);
			sl.setAmortissements((float) amortissement);
			sl.setInteretA((float) interestM);
			
			MontantRestant = MontantRestant - amortissement;
			interestM = (l.getInteret()/12) * MontantRestant;
			amortissement = calculMensualite(l.getMontantTotal(), l.getDureeDuCredit(),l.getInteret()) - interestM;

			
			echeanceRepository.save(sl);
			
		}
		return s;
	}

	@Override
	public List<Echeance> listAll() {
		 return (List<Echeance>) echeanceRepository.findAll();
	}

}
