package banque.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import banque.entities.Action;
import banque.entities.CompteTitre;
import banque.repositories.CompteTitreRepository;
import banque.repositories.ActionRepository;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;
@Service
public class ActionServiceImpl implements IActionService {
	@Autowired
	ActionRepository ActionRepository;
	@Autowired
	CompteTitreRepository CompteTitreRepository;
	
	
	@Override
	public Stock findStock(String ticker) {
		
		try 
		{
			return YahooFinance.get(ticker);
		}
		catch (IOException e)
		{
			System.out.println("Error");
		}
		return null;
		
	}
	
	@Override
	public BigDecimal findPrice(String ticker) throws IOException {
		
		Stock s = findStock(ticker);
		return s.getQuote(true).getPrice();
	}

	@Override
	public Object getHistory(String ticker, int year, String searchType) throws IOException {
		Calendar from = Calendar.getInstance();
		Calendar to = Calendar.getInstance();
		from.add(Calendar.YEAR, Integer.valueOf("-" + year));
	
		Stock stock = YahooFinance.get(ticker);
		List<HistoricalQuote> history = stock.getHistory(from, to, getInterval(searchType));
		for (HistoricalQuote quote : history) {
			System.out.println("====================================");
			System.out.println("symobol : " + quote.getSymbol());
			System.out.println("date : " + convertDate(quote.getDate()));
			System.out.println("High Price  : " + quote.getHigh());
			System.out.println("Low Price : " + quote.getLow());
			System.out.println("Closed price : " + quote.getClose());
			System.out.println("=========================================");
		}
		return history;
	}
	
	

	@Override
	public String convertDate(Calendar cal) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String formatDate = format.format(cal.getTime());
		return formatDate;
	}

	@Override
	public Interval getInterval(String searchType) {
		Interval interval = null;
		switch (searchType.toUpperCase()) {
		case "MONTHLY":
			interval = Interval.MONTHLY;
			break;
		case "WEEKLY":
			interval = Interval.WEEKLY;
			break;
		case "DAILY":
			interval = Interval.DAILY;
			break;

		}
		return interval;
	}

	@Override
	public Action addAction(Action a, Long idt) throws IOException {

		
		CompteTitre t=CompteTitreRepository.findById(idt).get();
		
		if ( t.getSolde().compareTo(a.getCapital()) >0) {  							//t.getSolde()> a.getCapital()
			t.setSolde( t.getSolde().subtract(a.getCapital()) );
			a.setTitreActions(CompteTitreRepository.findById(idt).orElse(null));
			
			
			Stock stock = YahooFinance.get(a.getSymbole());
			a.setHigh(stock.getQuote().getDayHigh());
			a.setLow(stock.getQuote().getDayLow());
			a.setVolume(stock.getQuote().getVolume());
			a.setClose(stock.getQuote().getPreviousClose());
			a.setValeurActuelle(a.getCapital());
			ActionRepository.save(a);
		}else throw new UnsupportedOperationException("Le mantant du portefeuille n' est pas suffisant");
		
		return a;
	}
	
	
	@Scheduled(cron = "*/30 * * * * *")
	public void updateActions() throws IOException {
		List<Action> allAction = (List<Action>) ActionRepository.findAll();
		
		for (Action a : allAction) {
			Stock stock = YahooFinance.get(a.getSymbole());
			a.setHigh(stock.getQuote().getDayHigh());
			a.setLow(stock.getQuote().getDayLow());
			a.setVolume(stock.getQuote().getVolume());
			
			
			a.setValeurActuelle(  a.getCapital().multiply(stock.getQuote().getPrice()).divide(a.getClose(),2, RoundingMode.HALF_UP));
			a.setClose(stock.getQuote().getPreviousClose());
			ActionRepository.save(a);
			}
		
	}
	
	@Override
	public void sellAction(long id) {
		Action a = ActionRepository.findById(id).orElse(null);
		a.getTitreActions().setSolde(a.getTitreActions().getSolde().add(a.getValeurActuelle()));
		ActionRepository.deleteById(id);
	}
	
	
	@Override
	public void deleteAction(long id) {
		ActionRepository.deleteById(id);
	}
	
	@Override
	public List <CompteTitre> retrieveCompteTitre(long id) {
		List<CompteTitre> c = (List<CompteTitre>) CompteTitreRepository.findAll();
		return c;
	}
	
	@Override
	public Action retrieveAction(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Action> retrieveAllActions() {
		List<Action> Actions = (List<Action>) ActionRepository.findAll();
		return Actions;
	}

	
	@Override
	public HashMap<Object, Object> portfolioPercentChange(int year) throws IOException {
		Calendar from = Calendar.getInstance();
		Calendar to = Calendar.getInstance();
		from.add(Calendar.YEAR, Integer.valueOf("-" + year));
		
		List<Action> Actions = (List<Action>) ActionRepository.findAll();
	
		HashMap<Object, Object> mapId = new LinkedHashMap<>();
		HashMap<Object, Object> map = new LinkedHashMap<>();
		for (Action a : Actions) {
			
			Stock stock = YahooFinance.get(a.getSymbole());
			List<HistoricalQuote> history = stock.getHistory(from, to,Interval.DAILY);
			 for(int i=0;i<history.size()-1;i++){
				stock.getQuote().getChangeInPercent();
				
				String date = convertDate(history.get(i+1).getDate()) ;
				
				map.put(date, (    (history.get(i+1).getClose().doubleValue() )    /// Percentage change = New Price / Old Price - 1
						/ (history.get(i).getClose().doubleValue() )  -   1));
				mapId.put(a.getSymbole(),map);
				
			}
			 map = new HashMap<>();
		}
		
	        return mapId;
	}
	
	@Override
    public HashMap<Object, Object> portfolioEsperance(int year) throws IOException {
		
		HashMap<Object, Object> mapChange = portfolioPercentChange(year);
		
		HashMap<Object, Object> map = new LinkedHashMap<>();
		for(Entry<Object, Object> t:mapChange.entrySet()) {
			double sum = 0;
			   String key = (String) t.getKey();
			   for (Entry<Object, Object> e : ((HashMap<Object, Object>) t.getValue()).entrySet()) {
				    sum = (double) e.getValue()+ sum;   
			   }
				map.put(key, sum/ ((HashMap<Object, Object>) t.getValue()).entrySet().size());
		}
		return map;
	}
	
	@Override
    public HashMap<Object, Object> portfolioEcartType(int year) throws IOException {
		
		HashMap<Object, Object> mapChange = portfolioPercentChange(year);
		HashMap<Object, Object> mapEsperence = portfolioEsperance(year);
		

		HashMap<Object, Object> map = new LinkedHashMap<>();
		
		for(Entry<Object, Object> s:mapEsperence.entrySet()) {
			String key2 = (String) s.getKey();
			for(Entry<Object, Object> t:mapChange.entrySet()) {
				double sum = 0;
				   String key = (String) t.getKey();
				   
				   for (Entry<Object, Object> e : ((HashMap<Object, Object>) t.getValue()).entrySet()) {
					   sum = Math.pow((double) e.getValue() + (double)s.getValue(),2) + sum ; 
					   
				   }
				 
				   map.put(key, Math.sqrt (sum / ( ((HashMap<Object, Object>) t.getValue()).entrySet().size() -1 )));
			}
			
			
			
		}
		return map;
	}
	
	@Override
    public HashMap<Object, Object> portfolioVarTheorique(int year,int time) throws IOException {
		HashMap<Object, Object> mapEcartType = portfolioEcartType(year);
		HashMap<Object, Object> mapEsperence = portfolioEsperance(year);
		
		List<Action> Actions = (List<Action>) ActionRepository.findAll();
		HashMap<Object, Object> map = new LinkedHashMap<>();
		double riskProbabilityLevel = 0.05;
		for(Entry<Object, Object> s:mapEsperence.entrySet()) {
			String key2 = (String) s.getKey();
			for(Action a : Actions){
				for(Entry<Object, Object> t:mapEcartType.entrySet()) {
				
				   String key = (String) t.getKey();
				   NormalDistribution distribution = new NormalDistribution((double)s.getValue(),(double) t.getValue()); //LOI.NORMALE.INVERSE(5%;esp;ecart)
				   double outcomeRisk = distribution.inverseCumulativeProbability(riskProbabilityLevel);  
				   map.put(key, outcomeRisk * a.getCapital().doubleValue() * Math.sqrt(time) );
				}
			
			}
			
		}
		return map;

	}	
	
	@Override
    public HashMap<Object, Object> portfolioVarHistorique(int year,double[] poids,int time) throws IOException {
		//HashMap<Object, Object> mapEcartType = portfolioEcartType(year);
		HashMap<Object, Object> mapChange = portfolioPercentChange(year);
		
		System.out.println(poids.length);
		HashMap<Object, Object> map = new LinkedHashMap<>();
		double riskProbabilityLevel = 0.05;
		for(Entry<Object, Object> s:mapChange.entrySet()) {
			String key = (String) s.getKey();
			for(int i=0;i<poids.length;i++){
				   for (Entry<Object, Object> e : ((HashMap<Object, Object>) s.getValue()).entrySet()) {
				   double distribution = new Percentile((double)s.getValue()).evaluate(riskProbabilityLevel); //LOI.NORMALE.INVERSE(5%;esp;ecart)
				  // double outcomeRisk = distribution.inverseCumulativeProbability(riskProbabilityLevel);  
				   map.put(key, distribution * poids[i] * Math.sqrt(time) );
				
			
				   }
			}
		}
		return map;

	}
		
	
}
