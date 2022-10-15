package banque.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity	
@Table(name="Tresorie")
public class Tresorie implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idTresorie;
	
	@Column(name = "MontantActuel")
	private float MontantActuel;
	
	@Column(name = "MontantRetrait")
	private float MontantRetrait;
	
	@Column(name = "MontantAjoute")
	private float MontantAjoute;
	
	@Column(name = "Source")
	private String Source;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "Date")
	private Date Date;
	
	@OneToMany(mappedBy = "TresorieTransactions")
	private Set<Transaction> Transactions;
}
