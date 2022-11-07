package banque.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import banque.entities.Action;
import banque.entities.CompteTitre;
import banque.services.IActionService;

@RestController
@RequestMapping("/action")
@CrossOrigin(origins = "*")
public class ActionController {
	@Autowired
	IActionService ActionService;

	@PostMapping("/data")
	  @ResponseBody
	  public BigDecimal findStock(@RequestParam String ticker) throws IOException {
		return  ActionService.findPrice(ticker);
	  }
	
	@PostMapping("/history")
	  @ResponseBody
	  public Object historyStock(@RequestParam String ticker, @RequestParam int year,@RequestParam String searchType  ) throws IOException {
		return  ActionService.getHistory(ticker, year, searchType);
	  }
	
	@RequestMapping(value="/add-action/{titre-id}", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public Action addSlice(@RequestBody Action a,@PathVariable("titre-id") Long IdC) throws IOException
	{
		Action action = ActionService.addAction(a,IdC);
		return action;
	}
	
	@DeleteMapping("/sell-action/{action-id}")
	@ResponseBody
	public void sellAction(@PathVariable("action-id") long id) {
	ActionService.sellAction(id);
	}
	
	@DeleteMapping("/delete-action/{action-id}")
	@ResponseBody
	public void deleteAction(@PathVariable("action-id") long id) {
	ActionService.deleteAction(id);
	}
	
	@GetMapping("/retrieve-titre/{titre-id}")
	@ResponseBody
	public List<CompteTitre> retrieveCompteTitre(@PathVariable("titre-id") long id) {
	return ActionService.retrieveCompteTitre(id);
	}
	
	@GetMapping("/retrieve-all-actions")
	@ResponseBody
	public List<Action> getLoans() {
	List<Action> listAction = ActionService.retrieveAllActions();
	return listAction;
	}
	
	@PostMapping("/change")
	  @ResponseBody
	  public HashMap<Object, Object> portfolioPercentChange(@RequestParam int year) throws IOException{
		return  ActionService.portfolioPercentChange(year);
	}
	
	@PostMapping("/esperance")
	  @ResponseBody
	  public HashMap<Object, Object> portfolioEsperance(@RequestParam int year) throws IOException{
		return  ActionService.portfolioEsperance(year);
	}
	
	@PostMapping("/EcartType")
	  @ResponseBody
	  public HashMap<Object, Object> portfolioEcartType(@RequestParam int year) throws IOException{
		return  ActionService.portfolioEcartType(year);
	}
	
	@PostMapping("/VarTheorique")
	  @ResponseBody
	  public HashMap<Object, Object> portfolioVarTheorique(@RequestParam int year,@RequestParam int time) throws IOException{
		return  ActionService.portfolioVarTheorique(year,time);
	}
	
	@PostMapping("/VarHistorique")
	  @ResponseBody
	  public HashMap<Object, Object> portfolioVarHistorique(@RequestParam int year,@RequestParam int time,@RequestBody double[] poids) throws IOException{
		return  ActionService.portfolioVarHistorique(year,poids,time);
	}
	

}
