package banque.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity	
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
	
	@Column(name = "Description")
	private String Description;
	
	@Column(name = "Interet")
	private float Interet;
	
	@Column(name = "DatePremierPaiement")
	private LocalDate DatePremierPaiement;
	
	
	@Column(name = "DateDernierPaiement")
	private LocalDate DateDernierPaiement;
	
	@Column(name = "StatusCredit")
	@Enumerated(EnumType.STRING)
	private StatusCredit StatusCredit;
	
	@Column(name = "ObjectifCredit")
	@Enumerated(EnumType.STRING)
	private ObjectifCredit ObjectifCredit;
	
	@Column(name = "Archive")
	private boolean Archive;
	
	@Column(name = "Score")
	private double Score;
	
	@JsonIgnore
	@OneToMany(mappedBy = "CreditEcheances")
	private Set<Echeance> Echeances;
	
	@JsonIgnore
	@OneToMany(mappedBy = "CreditAssurances")
	private Set<AssuranceCredit> assurances;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Employe> EmployesCredits;
	
	@JsonIgnore
	@ManyToOne
	private CompteCourant CourantCredits;
	
	
	
	
}
