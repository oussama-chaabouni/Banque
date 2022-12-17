package banque.entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class TransactionHistory {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idTransaction;
	
	private String Nom;
	
	private String rib;
	private String beneficiairerib;	
	@Enumerated(EnumType.STRING)
	private TypeTransaction typeTransaction;
	private float montant;
	private String motif;
	private String statut;
	private String codeRaison;
	private LocalDateTime dateOperation;
}
