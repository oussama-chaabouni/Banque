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
@Table(name="Reclamation")
public class Reclamation implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idReclamation;
	
	@Column(name = "Monatant")
	private float Monatant;
	
	@Column(name = "Source")
	private String Source;
	
	@Column(name = "Statut")
	private String Statut;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "creeLe")
	private Date creeLe;
	
	@ManyToMany(mappedBy="ReclamationsTransactions",cascade = CascadeType.ALL)
	private Set<Transaction> Transactions;
}
