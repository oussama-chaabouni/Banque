package banque.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import banque.entities.Action;
import banque.entities.Client;
import banque.entities.CompteTitre;
import banque.repositories.CompteTitreRepository;
import banque.repositories.ActionRepository;
import banque.repositories.ClientRepository;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
@Service
public class ActionServiceImpl implements IActionService {
	@Autowired
	ActionRepository ActionRepository;
	@Autowired
	CompteTitreRepository CompteTitreRepository;
	@Autowired
	ClientRepository ClientRepository;
	
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
	public HashMap<Object, Object> getCloseHistory(String ticker, int year, String searchType) throws IOException {
		Calendar from = Calendar.getInstance();
		Calendar to = Calendar.getInstance();
		from.add(Calendar.YEAR, Integer.valueOf("-" + year));
	
		Stock stock = YahooFinance.get(ticker);
		List<HistoricalQuote> history = stock.getHistory(from, to, getInterval(searchType));
		
		HashMap<Object, Object> map = new LinkedHashMap<>();
		
		 for(int i=0;i<history.size();i++){			
			String date = convertDate(history.get(i).getDate()) ;
			map.put(date, history.get(i).getClose());
		 }
		return map;
		
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
	
	
	//@Scheduled(cron = "*/30 * * * * *")
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
	public void sellAction(long id) throws IOException {
		Action a = ActionRepository.findById(id).orElse(null);
		Stock stock = YahooFinance.get(a.getSymbole());
		a.setValeurActuelle(  a.getCapital().multiply(stock.getQuote().getPrice()).divide(a.getClose(),2, RoundingMode.HALF_UP));
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
				
				String date = convertDate(history.get(i+1).getDate()) ;
				
				map.put(date, Math.log(    (history.get(i+1).getClose().doubleValue() )    /// Percentage change = Ln(New Price / Old Price )
						/ (history.get(i).getClose().doubleValue() )));
				mapId.put(a.getSymbole(),map);
				
			}
			 map = new LinkedHashMap<>();
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
		List<Action> Actions = (List<Action>) ActionRepository.findAll();
		
		for (Action a : Actions) {
			for(Entry<Object, Object> t:mapChange.entrySet()) {
				double sum = 0;
					   
				for (Entry<Object, Object> e : ((HashMap<Object, Object>) mapChange.get(a.getSymbole())).entrySet()) {
					sum = Math.pow((double) e.getValue() - (double)mapEsperence.get(a.getSymbole()),2) + sum ; 
				}
				
				map.put(a.getSymbole(), Math.sqrt (sum / ( ((HashMap<Object, Object>) t.getValue()).entrySet().size() -1 )));
			}
			
		}
		return map;
	}
	
	@Override
    public HashMap<Object, Object> portfolioVarTheorique(int year,int time,double confiance) throws IOException {
		HashMap<Object, Object> mapEcartType = portfolioEcartType(year);
		HashMap<Object, Object> mapEsperence = portfolioEsperance(year);
		
		List<Action> Actions = (List<Action>) ActionRepository.findAll();
		HashMap<Object, Object> map = new LinkedHashMap<>();
		double riskProbabilityLevel = (100 - confiance)/100;
			for(Action a : Actions){
				
				NormalDistribution distribution = new NormalDistribution((double)mapEsperence.get(a.getSymbole()),(double) mapEcartType.get(a.getSymbole())); //LOI.NORMALE.INVERSE(5%;esp;ecart)
				double outcomeRisk = distribution.inverseCumulativeProbability(riskProbabilityLevel);  
				map.put(a.getSymbole(), outcomeRisk * a.getCapital().doubleValue() * Math.sqrt(time) );
			}
		return map;

	}	
	
	@Override
    public HashMap<Object, Object> portfolioVarHistorique(int year,int time,double confiance) throws IOException {
		//HashMap<Object, Object> mapEcartType = portfolioEcartType(year);
		//distribution = StatUtils.percentile(d,5);			
		HashMap<Object, Object> mapChange = portfolioPercentChange(year);
		
		List<Action> Actions = (List<Action>) ActionRepository.findAll();
		HashMap<Object, Object> map = new LinkedHashMap<>();

			for(Action a : Actions){
				
				
				for(Entry<Object, Object> t:mapChange.entrySet()) {
					double [] d  = new double[((HashMap<Object, Object>) t.getValue()).entrySet().size() ];
					int i = 0;
					
					for (Entry<Object, Object> e : ((HashMap<Object, Object>) mapChange.get(a.getSymbole())).entrySet()) {
						d[i] = (double) e.getValue();
						i+=1;
					}
					   double distribution = new PercentileExcel().evaluate(d,100-confiance);
					   map.put(a.getSymbole(), distribution * a.getCapital().doubleValue() * Math.sqrt(time) );
			}

		}
		return map;
	}
	
	
	@Override
	public List<String> retrieveAllEmails() {
		return ClientRepository.getAllEmails();
	}
	
	
	ArrayList<BigDecimal> history = new ArrayList<BigDecimal>();
	
	ArrayList<BigDecimal> history2 = new ArrayList<BigDecimal>(Arrays.asList(BigDecimal.valueOf(1) ,BigDecimal.valueOf(2) ,
			BigDecimal.valueOf(3) ,BigDecimal.valueOf(4) ,BigDecimal.valueOf(3) ,BigDecimal.valueOf(4)
			
			));
	
	//@Scheduled(cron = "*/5 * * * * *")
	public void stream() throws IOException {
		String ticker = "BTC-USD";
		Stock stock = YahooFinance.get(ticker);
		history2.add(stock.getQuote(true).getPrice());
		
			 if (!history2.get( history2.size()-1).equals(history2.get( history2.size()-2))  ) {
				 history.add(history2.get( history2.size()-1));
				 System.out.println(history);
			 

		 }
	}
		
	
	@Override
	public Object history() {
		return history;
	}
	
	/*
	 @Override
	public HashMap<Object, Object> movingAverage(String ticker,int periode) throws IOException {
		Calendar from = Calendar.getInstance();
		Calendar to = Calendar.getInstance();
		from.add(Calendar.YEAR, Integer.valueOf("-" + 1));
		Stock stock = YahooFinance.get(ticker);
		// List<HistoricalQuote> history = stock.getHistory(from, to, Interval.DAILY);
		HashMap<Object, Object> map = new LinkedHashMap<>();
		ArrayList<BigDecimal> history = new ArrayList<BigDecimal>();
		for(int i=0; i <= periode;i++){		
			history.add(stock.getQuote(true).getPrice());
		}		
			 for(int i=0; i+periode <= history.size();i++){
				BigDecimal sum = BigDecimal.ZERO ;
				for (int j=i; j<i+ periode; j++) {	
					if (history.size()==periode) {
						history.remove(0)z
					}
					sum = history.get(j).add(sum);				
			}
				map.put("Moving Average " + periode +" "+ i,  sum.divide(BigDecimal.valueOf( periode), 2, RoundingMode.HALF_UP));
		}
		
	        return map;
	}
	 */
	
	@Override
	public LinkedHashMap<Object, Object> movingAverage(String ticker,int periode) throws IOException {
		//Calendar from = Calendar.getInstance();
		//Calendar to = Calendar.getInstance();
		//from.add(Calendar.YEAR, Integer.valueOf("-" + 1));
		//Stock stock = YahooFinance.get(ticker);
		// List<HistoricalQuote> history = stock.getHistory(from, to, Interval.DAILY);
		LinkedHashMap<Object, Object> map = new LinkedHashMap<>();
		//ArrayList<BigDecimal> history = new ArrayList<BigDecimal>();
		//for(int i=0; i <= periode;i++){		
		//	history.add(stock.getQuote(true).getPrice());
		//}		
			 for(int i=0; i+periode < history.size()+1;i++){
				BigDecimal sum = BigDecimal.ZERO ;
				for (int j=i; j<i+ periode; j++) {	
					if (history.size()==periode) {
						history.remove(0);
					}
					sum = history.get(j).add(sum);				
			}
				map.put("Moving Average " + periode +" "+ i,  sum.divide(BigDecimal.valueOf( periode), 3, RoundingMode.HALF_UP));
		}
		
	        return map;
	}
	
	
    public static BigDecimal
    getLast(LinkedHashMap<Object, Object> lhm)
    {
        int count = 1;
        BigDecimal last=null;
        for (Entry<Object, Object> it :
             lhm.entrySet()) {
           
            if (count == lhm.size()) {
               
            	 last = (BigDecimal) it.getValue();
               
            }
            count++;
        }
        return last;
    }
    
    
    Action a = new Action("BTC-USD",BigDecimal.valueOf(5));
    boolean position= false;
    long id=a.getIdAction();
    
    
	//@Scheduled(cron = "*/5 * * * * *")
	@Override
	public String movingAverageStrat() throws IOException {
		String ticker="BTC-USD";
	//	Action a = new Action();
	//	a.setSymbole(ticker);
	//	a.setCapital(BigDecimal.valueOf(1000));
		
		String d = "no action";
		if (history.size()>55) {
			LinkedHashMap<Object, Object> mapmapChange5 = movingAverage(ticker, 13);
			LinkedHashMap<Object, Object> mapmapChange10 = movingAverage(ticker, 21);
			LinkedHashMap<Object, Object> mapmapChange20 = movingAverage(ticker, 55);
		
		
		
		
		BigDecimal last5  = getLast(mapmapChange5 );
		BigDecimal last10  = getLast(mapmapChange10 );
		BigDecimal last20  = getLast(mapmapChange20 );
		//System.out.println((mapmapChange5));
		
		System.out.println(id);

		
					
					if (   ( last5.compareTo( last10)  ==1 )&& 
							
							( last5.compareTo( last20)  ==1 )  && (position==false)   )   {
							
						CompteTitre t=CompteTitreRepository.findById((long) 1).get();

						d="buy";
						Action a = new Action("BTC-USD",BigDecimal.valueOf(5));
						
						t.setSolde( t.getSolde().subtract(a.getCapital()) );
						addAction(a, (long) 1);
						CompteTitreRepository.save(t);
						position=true;
						id += 1;
					}else if (    ( last5.compareTo( last10)  ==-1 )&& 
							
							( last5.compareTo( last20)  ==-1 )  && (position==true)  )  {
						CompteTitre t=CompteTitreRepository.findById((long) 1).get();

						 d="sell";
						
						Stock stock = YahooFinance.get(a.getSymbole());
						a.setValeurActuelle(  a.getCapital().multiply(stock.getQuote().getPrice()).divide(a.getClose(),2, RoundingMode.HALF_UP));
						System.out.println(a.getValeurActuelle());
						t.setSolde(t.getSolde().add(a.getValeurActuelle()));

						sellAction(id);
						CompteTitreRepository.save(t);
						
					 position=false;
					}
				
		
		
	}
		System.out.println(d);

		return d;
	}
	
	
	/*
	@Override
	public String movingAverageStrat() throws IOException {
		String ticker="TSLA";
	//	Action a = new Action();
	//	a.setSymbole(ticker);
	//	a.setCapital(BigDecimal.valueOf(1000));
		
		String d = "no action";
		if (history.size()>5) {
		HashMap<Object, Object> mapmapChange5 = movingAverage(ticker, 3);
		HashMap<Object, Object> mapmapChange10 = movingAverage(ticker, 4);
		HashMap<Object, Object> mapmapChange20 = movingAverage(ticker, 5);
		Action a = new Action(ticker,BigDecimal.valueOf(1000));
		
		boolean position= false;
		for(Map.Entry<Object, Object> t:mapmapChange5.entrySet()) {
			for(Map.Entry<Object, Object> e:mapmapChange10.entrySet()) {
				for(Map.Entry<Object, Object> f:mapmapChange20.entrySet()) {
					
					if (   ( (BigDecimal) t.getValue()).compareTo( (BigDecimal) e.getValue())  >0 && 
							
							(( (BigDecimal) t.getValue()).compareTo( (BigDecimal) f.getValue())  >0)   && (position==false) )   {
				
							
						d="buy";
						addAction(a, (long) 1);
						position=true;
						
					}else if (   ( (BigDecimal) t.getValue()).compareTo( (BigDecimal) e.getValue())  <0 && 
					
							(( (BigDecimal) t.getValue()).compareTo( (BigDecimal) f.getValue())  <0)  && (position==true)  )  {
						 d="sell";
						
						 a = ActionRepository.findById(a.getIdAction()).orElse(null);
						sellAction(a.getIdAction());
						 position=false;
						
					}
				}
			}
		}
	}
		System.out.println(d);

		return d;
	}
	*/
}
