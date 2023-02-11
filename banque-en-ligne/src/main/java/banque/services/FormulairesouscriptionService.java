package banque.services;

import java.util.List;


import banque.entities.Formulairesouscription;

public interface FormulairesouscriptionService {

	List<Formulairesouscription> retrieveAllFormulairesouscriptions();
	Formulairesouscription retrieveFormulairesouscription(Long id);
	Formulairesouscription addFormulairesouscription(Formulairesouscription t);
	void deleteFormulairesouscription(Long id);
	Formulairesouscription updateFormulairesouscription(Formulairesouscription t);

	
	
}
