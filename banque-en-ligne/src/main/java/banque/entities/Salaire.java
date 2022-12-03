package banque.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name="Salaire")
public class Salaire implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idSalaire")
	private long idSalaire;
	
	@Column(name = "Salaire")
	private float Salaire;
	
	@Column(name = "SalaireNet")
	private float SalaireNet;
	
	@Column(name = "NbHeureSup")
	private float NbHeureSup;
	
	@Column(name = "PrixHeureSup")
	private float PrixHeureSup;
	
	@Column(name = "TotalTax")
	private float TotalTax;
	
	@OneToOne(mappedBy = "salaire")
    private Employe employe;
	
}
