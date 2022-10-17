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

	public long getIdObligation() {
		return idObligation;
	}

	public void setIdObligation(long idObligation) {
		this.idObligation = idObligation;
	}

	public float getMontant() {
		return Montant;
	}

	public void setMontant(float montant) {
		Montant = montant;
	}

	public Date getEchanceOblig() {
		return EchanceOblig;
	}

	public void setEchanceOblig(Date echanceOblig) {
		EchanceOblig = echanceOblig;
	}

	public float getCoupon() {
		return Coupon;
	}

	public void setCoupon(float coupon) {
		Coupon = coupon;
	}

	public int getMaturite() {
		return Maturite;
	}

	public void setMaturite(int maturite) {
		Maturite = maturite;
	}

	public float getTauxActu() {
		return TauxActu;
	}

	public void setTauxActu(float tauxActu) {
		TauxActu = tauxActu;
	}

	public float getValeurNominal() {
		return ValeurNominal;
	}

	public void setValeurNominal(float valeurNominal) {
		ValeurNominal = valeurNominal;
	}

	public float getTauxRendement() {
		return TauxRendement;
	}

	public void setTauxRendement(float tauxRendement) {
		TauxRendement = tauxRendement;
	}

	public CompteTitre getTitreObligations() {
		return TitreObligations;
	}

	public void setTitreObligations(CompteTitre titreObligations) {
		TitreObligations = titreObligations;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Obligation(long idObligation, float montant, Date echanceOblig, float coupon, int maturite, float tauxActu,
			float valeurNominal, float tauxRendement, CompteTitre titreObligations) {
		super();
		this.idObligation = idObligation;
		Montant = montant;
		EchanceOblig = echanceOblig;
		Coupon = coupon;
		Maturite = maturite;
		TauxActu = tauxActu;
		ValeurNominal = valeurNominal;
		TauxRendement = tauxRendement;
		TitreObligations = titreObligations;
	}
	
	
	
}
