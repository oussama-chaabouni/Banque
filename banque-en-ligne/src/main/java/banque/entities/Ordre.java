package banque.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity	
@Table(name="Ordre")
public class Ordre implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idOrdre")
	private long idOrdre;
	
	@Column(name = "Quantite")
	private int Quantite;
	
	@Column(name = "Prix")
	private float Prix;
	
	@Column(name = "TypeOrdre")
	@Enumerated(EnumType.STRING)
	private TypeOrdre TypeOrdre;
	
	@ManyToOne
	private CompteTitre TitreOrdres;
}
