package banque.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.NoArgsConstructor;

@Entity	
@Table(name="Credit")
@NoArgsConstructor
public class Credit implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCredit")
	private long idCredit;
	
	@Column(name = "MontantTotal")
	private int MontantTotal;
	@Column(name = "Description")
	private String DescriptionCredit;

	@Column(name = "DureeDuCredit")
	private int DureeDuCredit;
	
	@Column(name = "Interet")
	private float Interet;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DatePremierPaiement")
	private Date DatePremierPaiement;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DateDernierPaiement")
	private Date DateDernierPaiement;
	
	@Column(name = "StatusCredit")
	@Enumerated(EnumType.STRING)
	private StatusCredit StatusCredit;
	
	@Column(name = "ObjectifCredit")
	@Enumerated(EnumType.STRING)
	private ObjectifCredit ObjectifCredit;
	
	@Column(name = "Archive")
	private boolean Archive;
	
	@OneToMany(mappedBy = "CreditEcheances")
	private Set<Echeance> Echeances;
	
	
	
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Employe> EmployesCredits;
	
	@ManyToOne
	private CompteCourant CourantCredits;

	public long getIdCredit() {
		return idCredit;
	}

	public void setIdCredit(long idCredit) {
		this.idCredit = idCredit;
	}

	public int getMontantTotal() {
		return MontantTotal;
	}

	public void setMontantTotal(int montantTotal) {
		MontantTotal = montantTotal;
	}

	public int getDureeDuCredit() {
		return DureeDuCredit;
	}

	public void setDureeDuCredit(int dureeDuCredit) {
		DureeDuCredit = dureeDuCredit;
	}

	public float getInteret() {
		return Interet;
	}

	public void setInteret(float interet) {
		Interet = interet;
	}

	public Date getDatePremierPaiement() {
		return DatePremierPaiement;
	}

	public void setDatePremierPaiement(Date datePremierPaiement) {
		DatePremierPaiement = datePremierPaiement;
	}

	public Date getDateDernierPaiement() {
		return DateDernierPaiement;
	}

	public void setDateDernierPaiement(Date dateDernierPaiement) {
		DateDernierPaiement = dateDernierPaiement;
	}

	public StatusCredit getStatusCredit() {
		return StatusCredit;
	}

	public void setStatusCredit(StatusCredit statusCredit) {
		StatusCredit = statusCredit;
	}

	public ObjectifCredit getObjectifCredit() {
		return ObjectifCredit;
	}

	public void setObjectifCredit(ObjectifCredit objectifCredit) {
		ObjectifCredit = objectifCredit;
	}

	public boolean isArchive() {
		return Archive;
	}

	public void setArchive(boolean archive) {
		Archive = archive;
	}

	public Set<Echeance> getEcheances() {
		return Echeances;
	}

	public void setEcheances(Set<Echeance> echeances) {
		Echeances = echeances;
	}

	public Set<Employe> getEmployesCredits() {
		return EmployesCredits;
	}

	public void setEmployesCredits(Set<Employe> employesCredits) {
		EmployesCredits = employesCredits;
	}

	public CompteCourant getCourantCredits() {
		return CourantCredits;
	}

	public void setCourantCredits(CompteCourant courantCredits) {
		CourantCredits = courantCredits;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Credit(long idCredit, int montantTotal, int dureeDuCredit, float interet, Date datePremierPaiement,
			Date dateDernierPaiement, banque.entities.StatusCredit statusCredit,
			banque.entities.ObjectifCredit objectifCredit, boolean archive, Set<Echeance> echeances,
			Set<Employe> employesCredits, CompteCourant courantCredits) {
		super();
		this.idCredit = idCredit;
		MontantTotal = montantTotal;
		DureeDuCredit = dureeDuCredit;
		Interet = interet;
		DatePremierPaiement = datePremierPaiement;
		DateDernierPaiement = dateDernierPaiement;
		StatusCredit = statusCredit;
		ObjectifCredit = objectifCredit;
		Archive = archive;
		Echeances = echeances;
		EmployesCredits = employesCredits;
		CourantCredits = courantCredits;
	}

	public String getDescriptionCredit() {
		return DescriptionCredit;
	}

	public void setDescriptionCredit(String descriptionCredit) {
		DescriptionCredit = descriptionCredit;
	}
	
}
