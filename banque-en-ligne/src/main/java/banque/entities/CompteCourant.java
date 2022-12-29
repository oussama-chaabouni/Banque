package banque.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="CompteCourant")
public class CompteCourant implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCompteCourant")
	private long idCompteCourant;
	
	@Column(name = "RIBC")
	private String RIBC;
	
	@Column(name = "IBANC")
	private String IBANC;

	@Column(name = "Solde")
	private float Solde;
	
	
	@Column(name = "Plafond")
	private float Plafond;
	
	@ManyToOne
	private Client ClientCourants;
	
	@OneToMany(mappedBy = "CourantCredits")
	private Set<Credit> Credits;
	
	/*@OneToMany(cascade = CascadeType.ALL,mappedBy = "CourantTransactions")
	private Set<Transaction> TransactionsCourant; */
	
	
	@OneToMany(targetEntity=Transaction.class ,cascade = CascadeType.ALL)	
	@JoinColumn(name="rib",referencedColumnName="rib")
	private List<Transaction> transactions;
	
	
	//khater retrievelisttransactionsByRib ki kenet onetomany bel transaction
	@OneToMany(targetEntity=Reclamation.class ,cascade = CascadeType.ALL)	
	@JoinColumn(name="rib",referencedColumnName="rib")
	private List<Reclamation> reclamations;

}
