package banque.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import banque.entities.Client;
import banque.entities.CompteCourant;
import banque.entities.Credit;
import banque.entities.StatusCredit;
import banque.repositories.ClientRepository;
import banque.repositories.CreditRepo;
import banque.repositories.CreditRepository;

@Service
public class CreditServiceImpl implements ICreditService {
	
	@Autowired
	CreditRepository creditRepository;
	@Autowired
	ClientRepository clientRepository;
	
	

	@Override
	public String getDescById(Long idCredit) {
		Credit creditt = creditRepository.findById(idCredit).orElse(null);
	String desc=creditt.getDescriptionCredit();
		return desc;
		
	}

	
	
	
	//#############################Basic######################
	@Override
	public List<Credit> retrieveAllCredits() {
		
		return (List<Credit>) creditRepository.findAll() ; 
	}
	
	

	@Override
	public Credit addCredit(Credit c) {
		creditRepository.save(c);
		return c;
	}

	@Override
	public Credit updateCredit(Credit c) {
		creditRepository.save(c);
		return c;
	}

	@Override
	public Credit retrieveCredit(Long id) {
		creditRepository.findById(id);
		return creditRepository.findById(id).orElse(null);
	}

	@Override
	public void removeCredit(Long id) {
		creditRepository.deleteById(id);
		
	}
	
	
	@Override
	public Credit addLoanRequest(Credit l,Long IdC) {
		
	    Random rnd = new Random();
	    int number = rnd.nextInt(999999);
	    Client c = clientRepository.findById(IdC).orElse(null);
	    CompteCourant cc = c.getComptecourants().iterator().next();;
		l.setCourantCredits(cc);
	    System.out.println(c.getComptecourants());
		l.setStatusCredit(StatusCredit.EN_COURS);
		creditRepository.save(l);
		System.out.println(l.getIdCredit());
		l.setScore(predictScore(l.getIdCredit()));
		
		if( l.getScore()>350) {
			l.setInteret((float) 0.12);
		}else if( l.getScore()<350 && l.getScore()>280) {
			l.setInteret((float) 0.13);
		}else  {
			l.setInteret((float) 0.14);
		}
	
		

		creditRepository.save(l);
		
		return l;
		//l.setLoanRef(l.getIdL().toString() + l.getCustomerLoans().getFirstName().substring(0, 1)
		//+ l.getCustomerLoans().getLastName().substring(0, 1) + String.format("%06d", number) );
		
		//l.setScore(predictScore(l.getIdCredit()));
	
		/*if( l.getScore()>250) {
			l.setInteret((float) 0.12);
		}else if( l.getScore()<250 && l.getScore()>230) {
			l.setInteret((float) 0.13);
		}else  {
			l.setInteret((float) 0.13);
		}*/
		//l.setsetRemainAmount(l.getMontantTotal()-l.getPayedAmount());
		
		//loanRepository.save(l);
		//guaranteeRepository.save(g);
		
	}
	
	@Override
	public Credit acceptLoanRequest(Long id) {
		Credit l=creditRepository.findById(id).get();
		l.setStatusCredit(StatusCredit.ACCEPTE);
		l.setDatePremierPaiement(LocalDate.now());
		l.setDateDernierPaiement(LocalDate.now().plusMonths(l.getDureeDuCredit()));
		creditRepository.save(l);
		return l;
	}
	
	@Override
	public Credit denyLoanRequest(Long id) {
		Credit l=creditRepository.findById(id).get();
		l.setStatusCredit(StatusCredit.REFUSE);
		l.setArchive(true);
		creditRepository.save(l);
		return l;
	}

	
	@Override
	public Map<Object, Object> getScoreAttr(Long loanId) {
		LocalDate currentDate = LocalDate.now();
		Credit l=creditRepository.findById(loanId).get();
		HashMap<Object, Object> mapId = new HashMap<>();
		HashMap<Object, Object> map = new HashMap<>();
		//JSONObject jo = new JSONObject();
				Date dt=new Date();
				map.put("age",(dt.getYear()-l.getCourantCredits().getClient().getDateNaissance().getYear()));
				map.put("sex",l.getCourantCredits().getClient().getSexe());
				map.put("housing",l.getCourantCredits().getClient().getLogement());
				map.put("chekingAccount",l.getCourantCredits().getSolde());
				map.put("amount",l.getMontantTotal()/2);
				map.put("mois",l.getDureeDuCredit());
				map.put("purpose",l.getObjectifCredit());
				System.out.println(map);
	return map;	
	}
	
	
	@Override
	public Map<Object, Object> scoreDataTransform(String sex, String housing, double checkingAccount,String purpose) {
		
		HashMap<Object, Object> map = new HashMap<>();
		
		//float [] arr= new float[4];
		if (sex.equals("F")) {
			map.put("0",(float) 0.0);
		}else {
			map.put("0",(float) 1.0);
		}
		
		if (housing.equals("GRATUIT")) {
			map.put("1",(float) 0.0);
		}else if (housing.equals("PROPRIETAIRE")) {
			map.put("1",(float) 1.0);	
		}else if (housing.equals("LOCATION")) {
			map.put("1",(float) 2.0);
		}
		
		if (checkingAccount<=1000) {
			map.put("2",(float) 0.0);
		}else if ((checkingAccount>1000)&& (checkingAccount<=4000)) {
			map.put("2",(float) 1.0);
		}else if ((checkingAccount>4000)&& (checkingAccount<=10000)) {
			map.put("2",(float) 3.0);
		}else if (checkingAccount>10000) {
			map.put("2",(float) 2.0);
		}
		
		
		if(purpose.equals("Business")){
		map.put("3",(float) 0.0);
		}else if(purpose.equals("Voiture")){
			map.put("3",(float) 1.0);
		}else if(purpose.equals("Électroménager")){
			map.put("3",(float) 2.0);
		}else if(purpose.equals("Education")){
			map.put("3",(float) 3.0);
		}else if(purpose.equals("Mobilier_Equipement")){
			map.put("3",(float) 4.0);	
		}else if(purpose.equals("Radio_TV")){
			map.put("3",(float) 5.0);
		}else if(purpose.equals("Reparations")){
			map.put("3",(float) 6.0);
		}else if(purpose.equals("Vacances_autres"))
			map.put("3",(float) 7.0);


		return map;
	}
	
	
	@Override
public double  predictScore(long id) {
		
		RestTemplate restTemplate = new RestTemplate();
		Object response = restTemplate.getForObject("http://127.0.0.1:5000/predict/"+id, Object.class);
		 //Map<String, Object> map = oMapper.convertValue(response, Map.class);
		System.out.println(response);
	return  (double) response;
	}

	
	


}
