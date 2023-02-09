package banque.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter

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
	private String email;
	
	@Column(name = "Password")
	private String Password;
	
	@Column(name = "Role")
	@Enumerated(EnumType.STRING)
	private Role Role;
	
	@Column(name = "Departement")
	private String Departement;
	
	@Column(name="status")
	private boolean status;
	
	@Column(name="enabled")
	private boolean enabled;
	
	@Transient
	private String token;
	
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
 	@OneToMany(mappedBy = "EmployeConges")
	private Set<Conge> Conges;
	@JsonIgnore
	@OneToMany(mappedBy = "EmployeNotifications")
	private Set<Notification> Notifications;
	@JsonIgnore
	@OneToOne(mappedBy = "employeEmail")
	private EmailVerificationToken emailClient;
	
	@JsonIgnore
	@OneToOne(mappedBy = "agentToken")
	private RefreshToken agentToken;
}
