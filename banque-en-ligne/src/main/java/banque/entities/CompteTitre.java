package banque.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity	
@Table(name="CompteTitre")
public class CompteTitre implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCompteTitre")
	private long idCompteTitre;
	
	@Column(name = "Libelle")
	private String Libelle;

	@Column(name = "Solde")
	private BigDecimal Solde;
	
	@Column(name = "FraisTenue")
	private float FraisTenue;
	
	@ManyToOne
	private Client ClientTitres;
	
	@OneToMany(mappedBy = "TitreObligations")
	private Set<Obligation> Obligations;
	
	@OneToMany(mappedBy = "TitreOrdres")
	private Set<Ordre> Ordres;
	
	@OneToMany(mappedBy = "TitreActions")
	private Set<Action> Actions;

	public long getIdCompteTitre() {
		return idCompteTitre;
	}

	public void setIdCompteTitre(long idCompteTitre) {
		this.idCompteTitre = idCompteTitre;
	}

	public String getLibelle() {
		return Libelle;
	}

	public void setLibelle(String libelle) {
		Libelle = libelle;
	}

	public BigDecimal getSolde() {
		return Solde;
	}

	public void setSolde(BigDecimal solde) {
		Solde = solde;
	}

	public float getFraisTenue() {
		return FraisTenue;
	}

	public void setFraisTenue(float fraisTenue) {
		FraisTenue = fraisTenue;
	}
@JsonIgnore
	public Client getClientTitres() {
		return ClientTitres;
	}

	public void setClientTitres(Client clientTitres) {
		ClientTitres = clientTitres;
	}

	public Set<Obligation> getObligations() {
		return Obligations;
	}
	@JsonIgnore
	public void setObligations(Set<Obligation> obligations) {
		Obligations = obligations;
	}

	public Set<Ordre> getOrdres() {
		return Ordres;
	}

	public void setOrdres(Set<Ordre> ordres) {
		Ordres = ordres;
	}
	@JsonIgnore
	public Set<Action> getActions() {
		return Actions;
	}

	public void setActions(Set<Action> actions) {
		Actions = actions;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CompteTitre(long idCompteTitre, String libelle, BigDecimal solde, float fraisTenue, Client clientTitres,
			Set<Obligation> obligations, Set<Ordre> ordres, Set<Action> actions) {
		super();
		this.idCompteTitre = idCompteTitre;
		Libelle = libelle;
		Solde = solde;
		FraisTenue = fraisTenue;
		ClientTitres = clientTitres;
		Obligations = obligations;
		Ordres = ordres;
		Actions = actions;
	}
	
	
	public CompteTitre() {
		super();
	}
	
	
	
	
	
}
