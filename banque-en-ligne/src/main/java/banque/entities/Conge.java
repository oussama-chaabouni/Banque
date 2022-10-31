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
@Table(name="Conge")
public class Conge implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idConge")
	private long idConge;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "Duree")
	private Date Duree;
	
	@Column(name = "Description")
	private String Description;
	
	@ManyToOne
	private Credit EmployeConges;
}
