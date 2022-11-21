package banque.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name="Transaction")
public class Transaction implements Serializable{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idTransaction;
	private long rib;
	@Enumerated(EnumType.STRING)
	private TypeTransaction typeTransaction;
	private float montant;
	private String motif;
	/*
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Ribc")
	private CompteCourant ribCompteCourant;
	*/
	private String statut;
	private String codeRaison;
	private LocalDateTime dateOperation;
/*	
	@ManyToOne
	 CompteEpargne EpargneTransactions;
	
	
	@ManyToOne
	 CompteCourant CourantTransactions;	 */
	 
	/*
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy="transaction")
	private Set<Reclamation> reclamation;  */

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Solde")
    private CompteCourant soldeCompteCourant;



	

	
	
	
	
	

	
	
	
}
