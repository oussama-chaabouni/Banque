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
@Table(name="AssuranceCE")
public class AssuranceCE implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idAssuranceCE;
	
	@Column(name = "Nom")
	private String Nom;
	
	@Column(name = "Description")
	private String Description;
	
	@Column(name = "TypeAssurance")
	@Enumerated(EnumType.STRING)
	private TypeAssurance TypeAssurance;
	
	@ManyToOne
	private CompteEpargne EpargneAssurances;
}
