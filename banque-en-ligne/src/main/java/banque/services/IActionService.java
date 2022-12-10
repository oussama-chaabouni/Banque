package banque.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import banque.entities.Action;
import banque.entities.Client;
import banque.entities.CompteTitre;
import yahoofinance.Stock;
import yahoofinance.histquotes.Interval;

public interface IActionService {
	public Stock findStock(String ticker);
	public BigDecimal findPrice(String ticker) throws IOException;
	//public String refrech(String ticker)throws IOException ;
	public Object getHistory(String stockName, int year, String searchType) throws IOException;
	public String convertDate(Calendar cal) ;
	public Interval getInterval(String searchType);
	public Action addAction(Action a,Long idt) throws IOException;
	void sellAction(long id) throws IOException;
	public void deleteAction(long id);
	Action retrieveAction(long id);
	List<Action> retrieveAllActions();
	public HashMap<Object, Object>  portfolioPercentChange(int year) throws IOException;
	public HashMap<Object, Object> portfolioEsperance(int year) throws IOException;
	HashMap<Object, Object> portfolioEcartType(int year) throws IOException;
	HashMap<Object, Object> portfolioVarTheorique(int year,int time,double confiance) throws IOException;
	HashMap<Object, Object> portfolioVarHistorique(int year, int time,double confiance) throws IOException;
	List<CompteTitre> retrieveCompteTitre(long id);
	LinkedHashMap<Object, Object> movingAverage(String ticker,int periode) throws IOException;
	Object history();
	HashMap<Object, Object> getCloseHistory(String ticker, int year, String searchType) throws IOException;
	String movingAverageStrat() throws IOException;
	List<String> retrieveAllEmails();
}
