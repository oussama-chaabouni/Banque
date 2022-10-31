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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity	
@Table(name="Employe")
public class Employe implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idEmploye")
	private long idEmploye;
	
	@Column(name = "CIN")
	private String CIN;

	@Column(name = "Nom")
	private String Nom;
	
	@Column(name = "Prenom")
	private String Prenom;
	
	@Column(name = "Adresse")
	private String Adresse;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DateNaissance")
	private Date DateNaissance;
	
	@Column(name = "Email")
	private String Email;
	
	@Column(name = "Password")
	private String Password;
	
	@Column(name = "Role")
	@Enumerated(EnumType.STRING)
	private Role Role;
	
	@Column(name = "Departement")
	private String Departement;
	
	@ManyToMany(mappedBy="EmployesCredits",cascade = CascadeType.ALL)
	private Set<Credit> Credits;
	
	@ManyToMany(mappedBy="EmployesEmplois",cascade = CascadeType.ALL)
	private Set<Emplois> Emploiss;
	
	@ManyToMany(mappedBy="EmployesFormations",cascade = CascadeType.ALL)
	private Set<Formation> Formations;
	
	@ManyToMany(mappedBy="EmployesSalaires",cascade = CascadeType.ALL)
	private Set<Salaire> Salaires;
	
	@OneToMany(mappedBy = "EmployeConges")
	private Set<Conge> Conges;
	
	@OneToMany(mappedBy = "EmployeNotifications")
	private Set<Notification> Notifications;
	
}
