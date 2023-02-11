package banque.entities;

import java.io.Serializable;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
@Table(name="CompteEpargne")
public class CompteEpargne implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idCompteEpargne;
	
	@Column(name = "rib")
	private String rib;
	
	@Column(name = "IBANE")
	private String IBANE;
	
	@Column(name = "plafond")
	private int plafond;

	@Column(name = "Solde")
	private float Solde;
	
	@Column(name = "Interet")
	private float Interet;
	
	@Column(name = "TypeEpargne")
	@Enumerated(EnumType.STRING)
	private TypeEpargne TypeEpargne;
	
	/*  NA7ITHa KHATER 3AMLetli ERREUR KI JIT NAFFICHI LES COMPTES:java.lang.NumberFormatException: For input string: "Kenza" yekhi badaltha beli ta7tha
	@ManyToOne
    @JoinColumn(name = "nom")
    private Client clientepargne;
    */
	private String Nom;

	
	@OneToMany(mappedBy = "EpargneAssurances")
	private Set<AssuranceCE> AssurancesCE;
	
/*	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "EpargneTransactions")
	private Set<Transaction> TransactionsEpargne; */

	//khater retrievelisttransactionsByRib ki kenet onetomany bel transaction
	@OneToMany(targetEntity=Transaction.class ,cascade = CascadeType.ALL)	
	@JoinColumn(name="rib",referencedColumnName="rib")
	private List<Transaction> transactions;
	
	
	
}
