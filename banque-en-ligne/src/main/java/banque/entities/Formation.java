package banque.entities;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Formation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;
	private String Departement;
	private String Nom_Formation;
	private Integer Duree; // Par jours
	private String Description;
	private Date Date_debut;
	

	// Relation entre la Formation et technicien
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "formation")
	@JsonIgnore
	private List<Formation_Details> Formations_Details;
	@JsonIgnore
	public List<Formation_Details> getFormations_Details() {
		return Formations_Details;
	}

	public void setFormations_Details(List<Formation_Details> formations_Details) {
		Formations_Details = formations_Details;
	}

	// getters and setters
	

	public Formation() {
		super();
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getDepartement() {
		return Departement;
	}

	public void setDepartement(String departement) {
		Departement = departement;
	}

	public String getNom_Formation() {
		return Nom_Formation;
	}

	public void setNom_Formation(String nom_Formation) {
		Nom_Formation = nom_Formation;
	}

	public Integer getDuree() {
		return Duree;
	}

	public void setDuree(Integer duree) {
		Duree = duree;
	}

	

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public Date getDate_debut() {
		return Date_debut;
	}

	public void setDate_debut(Date date_debut) {
		Date_debut = date_debut;
	}

	public Formation(long id, String departement, String nom_Formation, Integer duree, String description,
			Date date_debut, List<Formation_Details> formations_Details) {
		super();
		Id = id;
		Departement = departement;
		Nom_Formation = nom_Formation;
		Duree = duree;
		Description = description;
		Date_debut = date_debut;
		Formations_Details = formations_Details;
	}
	
	
}
