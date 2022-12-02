package banque.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity	
@Table(name="Formation")
public class Formation{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idFormation")
	private long idFormation;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DateFormation")
	private Date DateFormation;
	
	@Column(name = "Titre")
	private String Titre;

	
	@Column(name = "Description")
	private String Description;
	
	@Column(name = "Departement")
	private String Departement;
	
	@Column(name = "Duree")
	private Integer Duree;


	
	// Relation entre la Formation et table formation_details
		@OneToMany(cascade = CascadeType.ALL, mappedBy = "formation")
		@JsonIgnore
		private List<Formation_Details> Formations_Details;



		public Formation(long idFormation, Date dateFormation, String titre, String description, String departement,
				Integer duree, List<Formation_Details> formations_Details) {
			super();
			this.idFormation = idFormation;
			DateFormation = dateFormation;
			Titre = titre;
			Description = description;
			Departement = departement;
			Duree = duree;
			Formations_Details = formations_Details;
		}



		public Formation(long idFormation, Date dateFormation, String titre, String description, String departement,
				Integer duree) {
			super();
			this.idFormation = idFormation;
			DateFormation = dateFormation;
			Titre = titre;
			Description = description;
			Departement = departement;
			Duree = duree;
		}



		public Formation() {
			super();
		}
	
	
}
