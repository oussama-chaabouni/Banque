package banque.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Formation_Details implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;
	
	
	private String Etat;

	// Relation avec entité formation et personnel (Porteuse de données)
	@ManyToOne
	@JoinColumn(name = "idEmploye")
	private Employe Employe;
	@ManyToOne
	@JoinColumn(name = "idF")
	private Formation formation;
	public long getId() {
		return Id;
	}
	public void setId(long id) {
		Id = id;
	}
	public String getEtat() {
		return Etat;
	}
	public void setEtat(String etat) {
		Etat = etat;
	}
	public Employe getEmploye() {
		return Employe;
	}
	public void setEmploye(Employe employe) {
		Employe = employe;
	}
	public Formation getFormation() {
		return formation;
	}
	public void setFormation(Formation formation) {
		this.formation = formation;
	}
	public Formation_Details(long id, String etat, banque.entities.Employe employe, Formation formation) {
		super();
		Id = id;
		Etat = etat;
		Employe = employe;
		this.formation = formation;
	}
	public Formation_Details() {
		super();
	}
	

}
