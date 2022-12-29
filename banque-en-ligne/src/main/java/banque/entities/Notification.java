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
@Table(name="Notification")
public class Notification implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idNotification")
	private long idNotification;
	
	@Column(name = "Contenu")
	private String Contenu;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DateEnvoi")
	private Date DateEnvoi;
	
	@Column(name = "Etat")
	private String Etat;
	
	@ManyToOne
	private Employe EmployeNotifications;
	
	@ManyToOne
	private Client ClientNotifications;
}
