package banque.entities;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
public class Formation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;
	private String Departement;
	private String Nom_Formation;
	private Integer Duree; // Par jours
	private String Description;
	private Date Date_debut;
	@Enumerated(EnumType.STRING)
	private Etatformation etatformation;
	

	// Relation entre la Formation et technicien
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "formation")
	@JsonIgnore
	private List<Formation_Details> Formations_Details;
	
	
	
	
}
