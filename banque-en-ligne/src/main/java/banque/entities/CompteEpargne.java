package banque.entities;

import java.io.Serializable;
import java.time.LocalDate;
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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity	
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    @JoinColumn(name = "nom")
    private Client clientepargne;
	

	
/*	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "EpargneTransactions")
	private Set<Transaction> TransactionsEpargne; */

	//khater retrievelisttransactionsByRib ki kenet onetomany bel transaction
	@OneToMany(targetEntity=Transaction.class ,cascade = CascadeType.ALL)	
	@JoinColumn(name="rib",referencedColumnName="rib")
	private List<Transaction> transactions;

	
}
