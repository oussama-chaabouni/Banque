package banque.entities;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class ScheduledInfoVirementPermanent {
		
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idTransaction;
	private String transferFrom;
	private String transferTo;
	private float montant;
	private String motif;
	private LocalDateTime startTime;
	
	private int duree; //9adech men mara: ex :5fois
	
	private int periode;  //chaque 9adech men jours ex: chaque 30jours(mois), 90(trimestre) ...
	
	
	
	
	
	

}
