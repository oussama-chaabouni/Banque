package banque.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import banque.entities.Assurance;
import banque.entities.Credit;
import banque.entities.HolidaysCalendar;
import banque.entities.ObjectifCredit;
import banque.entities.TypeAssurance;
import banque.repositories.AssuranceRepo;
import banque.repositories.CreditRepo;

@Service
public class AssuranceService implements IAssurance {
@Autowired
AssuranceRepo ar;
@Autowired
CreditRepo cr;
	@Override
	public List<Assurance> retrieveAllAssurances() {
		
		return (List<Assurance>) ar.findAll();
	}

	@Override
	public Assurance addAssurance(Assurance a) {
		ar.save(a);
		return a;
	}

	@Override
	public void deleteAssurance(Long id) {
		ar.deleteById(id);
		
	}

	@Override
	public Assurance updateAssurance(Assurance a) {
	ar.save(a);
	return a;
	}

	@Override
	public Assurance retrieveAssuranceById(Long id) {
		ar.findById(id);
		return ar.findById(id).orElse(null);
	}
	//Description , content based deescription, similarité cosinus
	/*@Override
	public String suggestionAssurance (Credit c, Assurance a) {
		String res="";
	List<Credit> credits=(List<Credit>) cr.findAll();
	for (Credit cre: credits) {
	ObjectifCredit oc1 = ObjectifCredit.Business;
	ObjectifCredit oc2 = ObjectifCredit.Voiture;
	ObjectifCredit oc3 = ObjectifCredit.Vacances_autres; 
	ObjectifCredit oc4 = ObjectifCredit.Education; 
		if (cre.getObjectifCredit()==oc1)
			{
			a.setTypeAssurance(TypeAssurance.INCENDIES);
			ar.save(a);
			
			res="Choisissez l'assurance Incendies pour votre Business";
			return res ;
			}
		else if (cre.getObjectifCredit()==oc2)
			{ar.save(a);
			a.setTypeAssurance(TypeAssurance.AUTO);
		res="Choisissez l'assurance AUTO pour votre Voiture";
			return res ;
			}
		else if (cre.getObjectifCredit()==oc3)
			{ar.save(a);
			a.setTypeAssurance(TypeAssurance.VIE);
		 res="Choisissez l'assurance Vie pour vos Vacances";
			return res ;
			}
		else if (cre.getObjectifCredit()==oc4)
		{ar.save(a);
		a.setTypeAssurance(TypeAssurance.VIE);
	 res="Choisissez l'assurance Vie pour votre Education";
		return res ;
		}

	
		
		
	}
	return res;
	}
	}
*/
public double  suggestionAssuranceV(Long id) {
	
	RestTemplate restTemplate = new RestTemplate();
	Object response = restTemplate.getForObject("http://127.0.0.1:5000/simv/"+id, Object.class);
	 //Map<String, Object> map = oMapper.convertValue(response, Map.class);
	System.out.println(response);

	return  (double) response;
}


public double  suggestionAssuranceB(Long id) {
	
	RestTemplate restTemplate = new RestTemplate();
	Object response = restTemplate.getForObject("http://127.0.0.1:5000/simb/"+id, Object.class);
	 //Map<String, Object> map = oMapper.convertValue(response, Map.class);
	System.out.println(response);

	return  (double) response;
}

public double  suggestionAssuranceE(Long id) {
	
	RestTemplate restTemplate = new RestTemplate();
	Object response = restTemplate.getForObject("http://127.0.0.1:5000/sime/"+id, Object.class);
	 //Map<String, Object> map = oMapper.convertValue(response, Map.class);
	System.out.println(response);

	return  (double) response;
}



public double  suggestionAssuranceVA(Long id) {
	
	RestTemplate restTemplate = new RestTemplate();
	Object response = restTemplate.getForObject("http://127.0.0.1:5000/simva/"+id, Object.class);
	 //Map<String, Object> map = oMapper.convertValue(response, Map.class);
	System.out.println(response);

	return  (double) response;
}
public String suggest(Long id) {
	String resSugg = null;
	Credit credit1=cr.findById(id).orElse(null);
	Assurance assurance1=ar.findById(id).orElse(null);
	double var1=suggestionAssuranceV(id);
	double var2=suggestionAssuranceB(id);
	double var3=suggestionAssuranceE(id);
	double var4=suggestionAssuranceVA(id);
	assurance1.setSuggestion(false);
	String descv="Choisissez l'assurance auto pour votre voiture";
	String descb="Choisissez l'assurance incendies";
	String desce="Choisissez l'assurance Vie pour vos etudes";
	String descva="Choisissez l'assurance vie";
	if (var1>0)
	{ assurance1.setSuggestion(true);
	assurance1.setDescription(descv);
	assurance1.setTypeAssurance(TypeAssurance.AUTO);
	System.out.println("Choisissez l'assurance voiture");
	resSugg="AUTO";
	}
	else if (var2>0)
	{ assurance1.setSuggestion(true);
	System.out.println("Choisissez l'assurance Incendies pour votre Business");
	assurance1.setDescription(descb);
	assurance1.setTypeAssurance(TypeAssurance.INCENDIES);
	resSugg="INCENDIES";
	}
	else if (var3>0)
	{ assurance1.setSuggestion(true);
	assurance1.setDescription(desce);
	assurance1.setTypeAssurance(TypeAssurance.VIE);
	System.out.println("Choisissez l'assurance Etudes");
	resSugg="VIE";
	}
	else if (var4>0)
	{ assurance1.setSuggestion(true);
	assurance1.setDescription(descva);
	assurance1.setTypeAssurance(TypeAssurance.VIE);
	System.out.println("Choisissez l'assurance Vie");
	resSugg="VIE";
	}
	return resSugg;
	
}


@Override
public String suggestionAssurance(Credit c, Assurance a) {
	// TODO Auto-generated method stub
	return null;
}
}

