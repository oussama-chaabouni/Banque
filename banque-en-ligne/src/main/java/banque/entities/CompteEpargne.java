package banque.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity	
@Table(name="CompteEpargne")
public class CompteEpargne implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idCompteEpargne;
	
	@Column(name = "RIBE")
	private String RIBE;
	
	@Column(name = "IBANE")
	private String IBANE;

	@Column(name = "Solde")
	private float Solde;
	
	@Column(name = "Interet")
	private float Interet;
	
	@Column(name = "TypeEpargne")
	@Enumerated(EnumType.STRING)
	private TypeEpargne TypeEpargne;
	
	@ManyToOne
	private Client ClientEpargnes;
	
	@OneToMany(mappedBy = "EpargneAssurances")
	private Set<AssuranceCE> AssurancesCE;
	
	@OneToMany(mappedBy = "EpargneTransactions")
	private Set<Transaction> TransactionsEpargne;

	
}
