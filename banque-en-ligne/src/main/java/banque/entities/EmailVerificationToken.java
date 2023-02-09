package banque.entities;

import java.io.Serializable;
import java.time.Instant;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="emailToken")
public class EmailVerificationToken implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Id")
	private Long IdEmailToken;
	@Column(name="token")
	private String token;
	@Column(name="expireDate")
	private Instant expireDate;
	
	@OneToOne
	private Employe employeEmail;
	@OneToOne
	private Client customerEmail;
	public EmailVerificationToken() {
		
	}
	
	public EmailVerificationToken(Long idEmailToken, String token, Instant expireDate, Employe employeEmail,
			Client customerEmail) {
		super();
		IdEmailToken = idEmailToken;
		this.token = token;
		this.expireDate = expireDate;
		this.employeEmail = employeEmail;
		this.customerEmail = customerEmail;
	}
	public EmailVerificationToken(Long idEmailToken, String token, Instant expireDate, Employe employeEmail) {
		super();
		IdEmailToken = idEmailToken;
		this.token = token;
		this.expireDate = expireDate;
		this.employeEmail = employeEmail;
	}
	public EmailVerificationToken(Long idEmailToken, String token, Instant expireDate, Client customerEmail) {
		super();
		IdEmailToken = idEmailToken;
		this.token = token;
		this.expireDate = expireDate;
		this.customerEmail = customerEmail;
	}

	public Long getIdEmailToken() {
		return IdEmailToken;
	}
	public void setIdEmailToken(Long idEmailToken) {
		IdEmailToken = idEmailToken;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Instant getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Instant expireDate) {
		this.expireDate = expireDate;
	}
	public Employe getEmployeEmail() {
		return employeEmail;
	}
	public void setEmployeEmail(Employe employeEmail) {
		this.employeEmail = employeEmail;
	}
	public Client getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(Client customerEmail) {
		this.customerEmail = customerEmail;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
	

}

