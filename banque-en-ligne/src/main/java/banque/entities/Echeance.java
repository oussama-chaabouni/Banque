package banque.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Echeance")
public class Echeance implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idEcheance")
	private long idEcheance;
	
	@Column(name = "InteretA")
	private float InteretA;
	
	@Column(name = "MantantRestant")
	private float MantantRestant;

	@Column(name = "Amortissements")
	private float Amortissements;
	
	@Column(name = "MontantPaye")
	private float MontantPaye;
	
	@Column(name = "Mensualite")
	private float Mensualite;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DateDeRemboursement")
	private Date DateDeRemboursement;

	@Column(name = "Verification")
	private boolean Verification;
	
	@ManyToOne
	private Credit CreditEcheances;
	
	
}
