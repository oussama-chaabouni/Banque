package banque.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity	
@Table(name="Salaire")
public class Salaire implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idSalaire")
	private long idSalaire;
	
	@Column(name = "Salaire")
	private float Salaire;
	
	@Column(name = "NbHeureSup")
	private float NbHeureSup;
	
	@Column(name = "PrixHeureSup")
	private float PrixHeureSup;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Employe> EmployesSalaires;
	
}
