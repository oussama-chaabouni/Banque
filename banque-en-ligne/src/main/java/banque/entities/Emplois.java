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
@Table(name="Emplois")
public class Emplois implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idEmplois")
	private long idEmplois;
	
	@Column(name = "Offre")
	private String Offre;

	@Column(name = "DescriptionEmploi")
	private String DescriptionEmploi;
	
	@Column(name = "Employeur")
	private String Employeur;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DateEmploi")
	private Date DateEmploi;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Employe> EmployesEmplois;
}
