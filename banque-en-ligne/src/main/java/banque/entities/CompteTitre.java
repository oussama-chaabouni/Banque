package banque.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	private float Solde;
	
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
}
