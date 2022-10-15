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

@Entity	
@Table(name="Credit")
public class Credit implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCredit")
	private long idCredit;
	
	@Column(name = "MontantTotal")
	private int MontantTotal;

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
	
	@OneToMany(mappedBy = "CreditAssurances")
	private Set<AssuranceCredit> assurances;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Employe> EmployesCredits;
	
	@ManyToOne
	private CompteCourant CourantCredits;
	
}
