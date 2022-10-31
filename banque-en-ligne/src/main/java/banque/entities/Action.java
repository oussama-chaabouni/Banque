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
@Table(name="Action")
public class Action implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idAction")
	private long idAction;
	
	@Column(name = "High")
	private float High;
	
	@Column(name = "Low")
	private float Low;
	
	@Column(name = "Volume")
	private float Volume;
	
	@Column(name = "Close")
	private float Close;
	
	@Column(name = "TypeOrdre")
	@Enumerated(EnumType.STRING)
	private TypeOrdre TypeOrdre;
	
	@ManyToOne
	private CompteTitre TitreActions;
	
}
