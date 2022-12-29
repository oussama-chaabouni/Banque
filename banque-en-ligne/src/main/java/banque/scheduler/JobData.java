package banque.scheduler;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import banque.entities.CompteCourant;
import banque.entities.CompteEpargne;
import banque.entities.Reclamation;
import banque.entities.Transaction;
import banque.entities.TypeTransaction;
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
public class JobData {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idTransaction;
	private String transferFrom;
	private String transferTo;
	private float montant;
	private String motif;
	private LocalDateTime startTime;
}
