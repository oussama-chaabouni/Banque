package banque.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity	
@Table(name="CompteEpargne")
public class CompteEpargne implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idCompteEpargne;
	
	@Column(name = "RIBE")
	private String RIBE;
	
	@Column(name = "IBANE")
	private String IBANE;

	@Column(name = "Solde")
	private float Solde;
	
	@Column(name = "Interet")
	private float Interet;
	
	@Column(name = "TypeEpargne")
	@Enumerated(EnumType.STRING)
	private TypeEpargne TypeEpargne;
	
	@ManyToOne
	private Client ClientEpargnes;
	
	@OneToMany(mappedBy = "EpargneAssurances")
	private Set<AssuranceCE> AssurancesCE;
	
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "EpargneTransactions")
	private Set<Transaction> TransactionsEpargne;

	public CompteEpargne() {
		super();
	}

	public CompteEpargne(long idCompteEpargne, String rIBE, String iBANE, float solde, float interet,
			TypeEpargne typeEpargne, Client clientEpargnes, Set<AssuranceCE> assurancesCE,
			Set<Transaction> transactionsEpargne) {
		super();
		this.idCompteEpargne = idCompteEpargne;
		RIBE = rIBE;
		IBANE = iBANE;
		Solde = solde;
		Interet = interet;
		TypeEpargne = typeEpargne;
		ClientEpargnes = clientEpargnes;
		AssurancesCE = assurancesCE;
		TransactionsEpargne = transactionsEpargne;
	}

	public long getIdCompteEpargne() {
		return idCompteEpargne;
	}

	public void setIdCompteEpargne(long idCompteEpargne) {
		this.idCompteEpargne = idCompteEpargne;
	}

	public String getRIBE() {
		return RIBE;
	}

	public void setRIBE(String rIBE) {
		RIBE = rIBE;
	}

	public String getIBANE() {
		return IBANE;
	}

	public void setIBANE(String iBANE) {
		IBANE = iBANE;
	}

	public float getSolde() {
		return Solde;
	}

	public void setSolde(float solde) {
		Solde = solde;
	}

	public float getInteret() {
		return Interet;
	}

	public void setInteret(float interet) {
		Interet = interet;
	}

	public TypeEpargne getTypeEpargne() {
		return TypeEpargne;
	}

	public void setTypeEpargne(TypeEpargne typeEpargne) {
		TypeEpargne = typeEpargne;
	}

	public Client getClientEpargnes() {
		return ClientEpargnes;
	}

	public void setClientEpargnes(Client clientEpargnes) {
		ClientEpargnes = clientEpargnes;
	}

	public Set<AssuranceCE> getAssurancesCE() {
		return AssurancesCE;
	}

	public void setAssurancesCE(Set<AssuranceCE> assurancesCE) {
		AssurancesCE = assurancesCE;
	}

	public Set<Transaction> getTransactionsEpargne() {
		return TransactionsEpargne;
	}

	public void setTransactionsEpargne(Set<Transaction> transactionsEpargne) {
		TransactionsEpargne = transactionsEpargne;
	}
	

	

	
	
	
}
