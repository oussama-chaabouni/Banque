package banque.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity	
@Table(name="Obligation")
public class Obligation implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idObligation")
	private long idObligation;
	
	@Column(name = "Montant")
	private float Montant;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "EchanceOblig")
	private Date EchanceOblig;

	@Column(name = "Coupon")
	private float Coupon;
	
	@Column(name = "Maturite")
	private int Maturite;
	
	@Column(name = "TauxActu")
	private float TauxActu;
	
	@Column(name = "ValeurNominal")
	private float  ValeurNominal;
	
	@Column(name = "TauxRendement")
	private float  TauxRendement;
	
	@ManyToOne
	private CompteTitre TitreObligations;
	
}
