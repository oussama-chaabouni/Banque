package banque.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity	
@Table(name="CompteCourant")
public class CompteCourant implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCompteCourant")
	private long idCompteCourant;
	
	@Column(name = "RIBC")
	private String RIBC;
	
	@Column(name = "IBANC")
	private String IBANC;

	@Column(name = "Solde")
	private float Solde;
	
	
	@Column(name = "Plafond")
	private float Plafond;
	
	@ManyToOne
	private Client ClientCourants;
	
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "CourantCredits")
	private Set<Credit> Credits;
	
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "CourantTransactions")
	private Set<Transaction> TransactionsCourant;

	
	public CompteCourant() {
		super();
	}

	public CompteCourant(long idCompteCourant, String rIBC, String iBANC, float solde, float plafond,
			Client clientCourants, Set<Credit> credits, Set<Transaction> transactionsCourant) {
		super();
		this.idCompteCourant = idCompteCourant;
		RIBC = rIBC;
		IBANC = iBANC;
		Solde = solde;
		Plafond = plafond;
		ClientCourants = clientCourants;
		Credits = credits;
		TransactionsCourant = transactionsCourant;
	}

	public long getIdCompteCourant() {
		return idCompteCourant;
	}

	public void setIdCompteCourant(long idCompteCourant) {
		this.idCompteCourant = idCompteCourant;
	}

	public String getRIBC() {
		return RIBC;
	}

	public void setRIBC(String rIBC) {
		RIBC = rIBC;
	}

	public String getIBANC() {
		return IBANC;
	}

	public void setIBANC(String iBANC) {
		IBANC = iBANC;
	}

	public float getSolde() {
		return Solde;
	}

	public void setSolde(float solde) {
		Solde = solde;
	}

	public float getPlafond() {
		return Plafond;
	}

	public void setPlafond(float plafond) {
		Plafond = plafond;
	}

	public Client getClientCourants() {
		return ClientCourants;
	}

	public void setClientCourants(Client clientCourants) {
		ClientCourants = clientCourants;
	}

	public Set<Credit> getCredits() {
		return Credits;
	}

	public void setCredits(Set<Credit> credits) {
		Credits = credits;
	}

	public Set<Transaction> getTransactionsCourant() {
		return TransactionsCourant;
	}

	public void setTransactionsCourant(Set<Transaction> transactionsCourant) {
		TransactionsCourant = transactionsCourant;
	}
	
	


	

	
	
	
	
}
