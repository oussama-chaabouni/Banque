package banque.entities;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name="Client")
public class Client implements Serializable {
private static final long serialVersionUID = 1L;
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "idClient")
private long idClient;

@Column(name = "CIN")
private String CIN;

@Column(name = "Nom")
private String Nom;

@Column(name = "Prenom")
private String Prenom;

@Column(name = "Sexe")
private String Sexe;

@Column(name = "Adresse")
private String Adresse;

@Temporal(TemporalType.DATE)
@Column(name = "DateNaissance")
private Date DateNaissance;

@Column(name = "Email")
private String email;

@Column(name = "Password")
private String Password;

@Column(name = "Salaire")
private float Salaire;

@Column(name = "Profession")
private String Profession;

@Column(name = "Logement")
@Enumerated(EnumType.STRING)
private Logement Logement;

@Column(name="status")
private boolean status;
@Column(name="enabled")
private boolean enabled;
@Lob
@Column(name = "imagedata")
private byte[] imageData;

@Transient
private String token;
@OneToMany(mappedBy = "ClientNotifications")
private Set<Notification> Notifications;
@OneToMany(mappedBy = "ClientTitres")
private Set<CompteTitre> CompteTitres;

/*
@OneToMany(mappedBy = "ClientCourants")
private Set<CompteCourant> CompteCourants; */
@JsonIgnore
@OneToMany(mappedBy = "client")
    private Set<CompteCourant> comptecourants = new HashSet<>();

@OneToMany(mappedBy = "clientepargne")
private Set<CompteEpargne> clientepargnes = new HashSet<>();
@JsonIgnore
@OneToOne(mappedBy = "customerEmail")
private EmailVerificationToken emailCustomer;

@JsonIgnore
@OneToOne(mappedBy = "customerToken")
private RefreshToken customerToken;




}