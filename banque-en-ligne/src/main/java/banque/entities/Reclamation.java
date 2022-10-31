package banque.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import banque.entities.Transaction;

@Entity	
@Table(name="Reclamation")
public class Reclamation implements Serializable{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idReclamation;
	
	@JsonBackReference
	@ManyToOne
	Transaction transaction;
	long compteCourantId;
    private String transaction_type;
	private float montant;
	private String source;
	private String statut;
	private LocalDateTime creeLe;
	public Reclamation() {
		super();
	}
	
	public Reclamation(long idReclamation, Transaction transaction,long compteCourantId, String transaction_type,float montant, String source, String statut,
			LocalDateTime creeLe) {
		
		super();
		this.idReclamation = idReclamation;
		this.transaction = transaction;
		this.compteCourantId = compteCourantId;
		this.transaction_type = transaction_type;
		this.montant = montant;
		this.source = source;
		this.statut = statut;
		this.creeLe = creeLe;
	}
	public long getIdReclamation() {
		return idReclamation;
	}
	public void setIdReclamation(long idReclamation) {
		this.idReclamation = idReclamation;
	}
	public Transaction getTransaction() {
		return transaction;
	}
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	public long getCompteCourantId() {
		return compteCourantId;
	}
	public void setCompteCourantId(long compteCourantId) {
		this.compteCourantId = compteCourantId;
	}
	
	public String getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
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
	public LocalDateTime getCreeLe() {
		return creeLe;
	}
	public void setCreeLe(LocalDateTime creeLe) {
		this.creeLe = creeLe;
	}
	
	
	
	
	
	
	
	
	
	
}
