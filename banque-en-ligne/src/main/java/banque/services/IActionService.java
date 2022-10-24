package banque.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import banque.entities.Action;
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
	Action retrieveAction(long id);
	List<Action> retrieveAllActions();
	public HashMap<Object, Object>  portfolioPercentChange(int year) throws IOException;
	public HashMap<Object, Object> portfolioEsperance(int year) throws IOException;
	HashMap<Object, Object> portfolioEcartType(int year) throws IOException;
	HashMap<Object, Object> portfolioVarTheorique(int year,double[] poids,int time) throws IOException;
}
