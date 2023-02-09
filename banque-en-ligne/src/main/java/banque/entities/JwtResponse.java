package banque.entities;

import java.io.Serializable;

public class JwtResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private final String JWTToken;
	private final String RefreshToken;
	
	private Employe agent;
	private Client client;
	
	
	public JwtResponse(String jWTToken, String refreshToken) {
		super();
		JWTToken = jWTToken;
		RefreshToken = refreshToken;
	}
	
	public JwtResponse(String jWTToken, String refreshToken, Employe agent) {
		super();
		JWTToken = jWTToken;
		RefreshToken = refreshToken;
		this.agent = agent;
	}


	public JwtResponse(String jWTToken, String refreshToken, Client client) {
		super();
		JWTToken = jWTToken;
		RefreshToken = refreshToken;
		this.client = client;
	}

	public String getJWTToken() {
		return JWTToken;
	}

	public Employe getAgent() {
		return agent;
	}

	public void setAgent(Employe agent) {
		this.agent = agent;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getRefreshToken() {
		return RefreshToken;
	}
	

}
