package banque.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity	
@Table(name="AssuranceCredit")
public class AssuranceCredit implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idAssuranceCredit")
	private long idAssuranceCredit;
	
	@Column(name = "Description")
	private String Description;

	@Temporal(TemporalType.DATE)
	@Column(name = "DateContrat")
	private Date DateContrat;
	
	@Column(name = "Montant")
	private float Montant;
	
	@ManyToOne
	private Credit CreditAssurances;
	

}
