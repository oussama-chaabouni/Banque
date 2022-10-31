package banque.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Payement")
public class Payement implements Serializable{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idPayement;
	private long compteCourantId;
	private String beneficiaire;
	private String beneficiaireCourantAcc;
	private float montant;
	private String reference;
	private String statut;
	private String codeRaison;
	private LocalDateTime dateOperation;
	
	
	
	public Payement() {
		super();
	}


	public Payement(long idPayement, long compteCourantId, String beneficiaire, String beneficiaireCourantAcc,
			float montant, String reference, String statut, String codeRaison, LocalDateTime dateOperation) {
		super();
		this.idPayement = idPayement;
		this.compteCourantId = compteCourantId;
		this.beneficiaire = beneficiaire;
		this.beneficiaireCourantAcc = beneficiaireCourantAcc;
		this.montant = montant;
		this.reference = reference;
		this.statut = statut;
		this.codeRaison = codeRaison;
		this.dateOperation = dateOperation;
	}



	public long getIdPayement() {
		return idPayement;
	}


	public void setIdPayement(long idPayement) {
		this.idPayement = idPayement;
	}


	public long getCompteCourantId() {
		return compteCourantId;
	}


	public void setCompteCourantId(long compteCourantId) {
		this.compteCourantId = compteCourantId;
	}


	public String getBenificiaire() {
		return beneficiaire;
	}


	public void setBenificiaire(String beneficiaire) {
		this.beneficiaire = beneficiaire;
	}


	public String getBeneficiaireCourantAcc() {
		return beneficiaireCourantAcc;
	}


	public void setBeneficiaireCourantAcc(String beneficiaireCourantAcc) {
		this.beneficiaireCourantAcc = beneficiaireCourantAcc;
	}


	public float getMontant() {
		return montant;
	}


	public void setMontant(float montant) {
		this.montant = montant;
	}


	public String getReference() {
		return reference;
	}


	public void setReference(String reference) {
		this.reference = reference;
	}


	public String getStatut() {
		return statut;
	}


	public void setStatut(String statut) {
		this.statut = statut;
	}


	public String getCodeRaison() {
		return codeRaison;
	}


	public void setCodeRaison(String codeRaison) {
		this.codeRaison = codeRaison;
	}


	public LocalDateTime getDateOperation() {
		return dateOperation;
	}


	public void setDateOperation(LocalDateTime dateOperation) {
		this.dateOperation = dateOperation;
	}
	
	
	
	

}
