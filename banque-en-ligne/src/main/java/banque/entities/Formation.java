package banque.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity	
@Table(name="Formation")
public class Formation implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idFormation")
	private long idFormation;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DateFormation")
	private Date DateFormation;
	
	@Column(name = "Titre")
	private String Titre;

	@Column(name = "Organisateur")
	private String Organisateur;
	
	@Column(name = "Description")
	private String Description;
	
	@Column(name = "Departement")
	private String Departement;

	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Employe> EmployesFormations;
	
	
}
