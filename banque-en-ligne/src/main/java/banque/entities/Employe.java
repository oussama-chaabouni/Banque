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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


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
	@JsonIgnore

	@ManyToMany(mappedBy="EmployesCredits",cascade = CascadeType.ALL)
	private Set<Credit> Credits;
	@JsonIgnore
	@ManyToMany(mappedBy="EmployesEmplois",cascade = CascadeType.ALL)
	private Set<Emplois> Emploiss;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "Employe")
	private List<Formation_Details> Formations_Details;
		@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_salaire", referencedColumnName = "idSalaire")
	private Salaire salaire;
	
 @JsonIgnore
	@ManyToMany(mappedBy="EmployesSalaires",cascade = CascadeType.ALL)
	private Set<Salaire> Salaires;
	
	
 	@JsonIgnore
 	@OneToMany(mappedBy = "EmployeConges")
	private Set<Conge> Conges;
	@JsonIgnore
	@OneToMany(mappedBy = "EmployeNotifications")
	private Set<Notification> Notifications;

	public long getIdEmploye() {
		return idEmploye;
	}

	public void setIdEmploye(long idEmploye) {
		this.idEmploye = idEmploye;
	}

	public String getCIN() {
		return CIN;
	}

	public void setCIN(String cIN) {
		CIN = cIN;
	}

	public String getNom() {
		return Nom;
	}

	public void setNom(String nom) {
		Nom = nom;
	}

	public String getPrenom() {
		return Prenom;
	}

	public void setPrenom(String prenom) {
		Prenom = prenom;
	}

	public String getAdresse() {
		return Adresse;
	}

	public void setAdresse(String adresse) {
		Adresse = adresse;
	}

	public Date getDateNaissance() {
		return DateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		DateNaissance = dateNaissance;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public Role getRole() {
		return Role;
	}

	public void setRole(Role role) {
		Role = role;
	}

	public String getDepartement() {
		return Departement;
	}

	public void setDepartement(String departement) {
		Departement = departement;
	}

	public Set<Credit> getCredits() {
		return Credits;
	}

	public void setCredits(Set<Credit> credits) {
		Credits = credits;
	}

	public Set<Emplois> getEmploiss() {
		return Emploiss;
	}

	public void setEmploiss(Set<Emplois> emploiss) {
		Emploiss = emploiss;
	}

	public Set<Formation> getFormations() {
		return Formations;
	}

	public void setFormations(Set<Formation> formations) {
		Formations = formations;
	}

	public Set<Salaire> getSalaires() {
		return Salaires;
	}

	public void setSalaires(Set<Salaire> salaires) {
		Salaires = salaires;
	}

	public Set<Conge> getConges() {
		return Conges;
	}

	public void setConges(Set<Conge> conges) {
		Conges = conges;
	}

	public Set<Notification> getNotifications() {
		return Notifications;
	}

	public void setNotifications(Set<Notification> notifications) {
		Notifications = notifications;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
