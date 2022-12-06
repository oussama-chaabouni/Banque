package banque.entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name="Simulateuragios")
public class Simulateuragios {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	long idagios;
	float montantdudecouvert;
	long dureedudecouvert;
	float tauxannueleffectifglobal;
	int nombredejoursdanslannée;
	float totalagios;
	

}
