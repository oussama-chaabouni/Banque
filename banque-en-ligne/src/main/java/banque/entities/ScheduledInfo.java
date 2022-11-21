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
public class ScheduledInfo {
	
	//YODHHORLI CHEN7OT KEN TRANSFERFROM W TRANSFERTO MONTANT W STARTTIME W MBA3D N3AMMER F TABLE TRANSACTION KIMA NHEB ENA fi CLASS SCHEDUEDJOB.JAVA
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idTransaction;
	private long transferFrom;
	private long transferTo;
	private float montant;
	private String motif;
	private LocalDateTime startTime;
	
	
	
	
	
	
	

}
