package banque.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import banque.entities.Formulairesouscription;
import banque.repositories.FormulairesouscriptionRepository;

@Service
public class FormulairesouscriptionServiceImpl implements FormulairesouscriptionService {
	
	@Autowired
	FormulairesouscriptionRepository FormulairesouscriptionRep;


	@Override
	public List<Formulairesouscription> retrieveAllFormulairesouscriptions() {
		List<Formulairesouscription> formulairesouscriptions= (List<Formulairesouscription>) (FormulairesouscriptionRep.findAll());
		//traja3ali de type iterable , donc nrodha traja3 type formulairesouscription
		return formulairesouscriptions;
	}

	@Override
	public Formulairesouscription retrieveFormulairesouscription(Long id) {
		return FormulairesouscriptionRep.findById(id).get();
	}

	@Override
	public Formulairesouscription addFormulairesouscription(Formulairesouscription t) {
		return FormulairesouscriptionRep.save(t);
	}
	

	@Override
	public void deleteFormulairesouscription(Long id) {
		FormulairesouscriptionRep.deleteById(id);

	}

	@Override
	public Formulairesouscription updateFormulairesouscription(Formulairesouscription t) {
		return FormulairesouscriptionRep.save(t);
	}
	


	



	
		
	
	
	
}
