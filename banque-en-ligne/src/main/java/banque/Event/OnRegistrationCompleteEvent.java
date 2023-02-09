package banque.Event;
import java.util.Locale;

import org.springframework.cglib.core.Local;
import org.springframework.context.ApplicationEvent;

import banque.entities.Client;
import banque.entities.Employe;
public class OnRegistrationCompleteEvent extends ApplicationEvent {
	
	
	
	private Employe agent;
	private Client customer;

	
	public OnRegistrationCompleteEvent( Employe agent) {
		super(agent);
		
		this.agent = agent;
	}

	public OnRegistrationCompleteEvent(Client customer) {
		super(customer);
		
		this.customer = customer;
	}

	public Employe getAgent() {
		return agent;
	}

	public void setAgent(Employe agent) {
		this.agent = agent;
	}

	public Client getCustomer() {
		return customer;
	}

	public void setCustomer(Client customer) {
		this.customer = customer;
	}
		

}

