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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity	
@Table(name="Transaction")
public class Transaction implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idTransaction;
	
	@Column(name = "Montant")
	private float Montant;
	
	@Column(name = "Source")
	private String Source;
	
	@Column(name = "Statut")
	private String Statut;
	
	@Column(name = "Raison")
	private String Raison;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "CreeLe")
	private Date CreeLe;
	
	@Column(name = "TypeTransaction")
	@Enumerated(EnumType.STRING)
	private TypeTransaction TypeTransaction;
	
	@ManyToOne
	private CompteEpargne EpargneTransactions;
	
	@ManyToOne
	private CompteCourant CourantTransactions;
	
	
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Reclamation> ReclamationsTransactions;
}
