package banque.entities;

import java.io.Serializable;
import java.sql.Blob;
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
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Table(name="formulairesouscription")

public class Formulairesouscription implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Enumerated(EnumType.STRING)
	private Titredecivilite titredecivilite;
	private String nom;
	private String prenom;
	@Temporal(TemporalType.DATE)
	private Date datenaissance;
	private long numero;
	private String email;
	
	@Enumerated(EnumType.STRING)
	private Titredecivilite titredecivilitetuteur;
	private String nomtuteur;
	private String prenomtuteur;
	private String datenaissancetuteur;
	private String numerotuteur;
	private String emailtuteur;
	@Lob
	private byte[] pieceditentite;
	@Lob
	private byte[] justificatifdedomicile;
	private long depotinitial;
	
	
	
	


	

	
	


	

	
	
	
	
}
