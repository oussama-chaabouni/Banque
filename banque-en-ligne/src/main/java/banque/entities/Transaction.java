package banque.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
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
@Table(name="Transaction")
public class Transaction implements Serializable{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idTransaction;
	private long compteCourantId;
	@Enumerated(EnumType.STRING)
	private TypeTransaction typeTransaction;
	private float montant;
	private String source;
	private String statut;
	private String codeRaison;
	private LocalDateTime dateOperation;
	
	@ManyToOne
	 CompteEpargne EpargneTransactions;
	
	@ManyToOne
	 CompteCourant CourantTransactions;	
	
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy="transaction")
	private Set<Reclamation> reclamation;

	public Transaction() {
		super();
	}

	public Transaction(long idTransaction,long compteCourantId, TypeTransaction typeTransaction, float montant, String source, String statut,
			String codeRaison, LocalDateTime dateOperation, CompteEpargne epargneTransactions, CompteCourant courantTransactions,
			Set<Reclamation> reclamation) {
		super();
		this.idTransaction = idTransaction;
		this.compteCourantId = compteCourantId;
		this.typeTransaction = typeTransaction;
		this.montant = montant;
		this.source = source;
		this.statut = statut;
		this.codeRaison = codeRaison;
		this.dateOperation = dateOperation;
		EpargneTransactions = epargneTransactions;
		CourantTransactions = courantTransactions;
		this.reclamation = reclamation;
	}

	public long getIdTransaction() {
		return idTransaction;
	}

	public void setIdTransaction(long idTransaction) {
		this.idTransaction = idTransaction;
	}
	
	public long getCompteCourantId() {
		return compteCourantId;
	}

	public void setCompteCourantId(long compteCourantId) {
		this.compteCourantId = compteCourantId;
	}

	public TypeTransaction getTypeTransaction() {
		return typeTransaction;
	}

	public void setTypeTransaction(TypeTransaction typeTransaction) {
		this.typeTransaction = typeTransaction;
	}

	public float getMontant() {
		return montant;
	}

	public void setMontant(float montant) {
		this.montant = montant;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public String getRaison() {
		return codeRaison;
	}

	public void setRaison(String codeRaison) {
		this.codeRaison = codeRaison;
	}

	public LocalDateTime getCreeLe() {
		return dateOperation;
	}

	public void setCreeLe(LocalDateTime dateOperation) {
		this.dateOperation = dateOperation;
	}

	public CompteEpargne getEpargneTransactions() {
		return EpargneTransactions;
	}

	public void setEpargneTransactions(CompteEpargne epargneTransactions) {
		EpargneTransactions = epargneTransactions;
	}

	public CompteCourant getCourantTransactions() {
		return CourantTransactions;
	}

	public void setCourantTransactions(CompteCourant courantTransactions) {
		CourantTransactions = courantTransactions;
	}

	public Set<Reclamation> getReclamation() {
		return reclamation;
	}

	public void setReclamation(Set<Reclamation> reclamation) {
		this.reclamation = reclamation;
	}

	

	
	
	
	
	

	
	
	
}
