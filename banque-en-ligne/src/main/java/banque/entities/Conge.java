package banque.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;

import lombok.NoArgsConstructor;
@NoArgsConstructor
@Entity	
@Table(name="Conge")
public class Conge implements Serializable{
	private static final long serialVersionUID = 1L;
	
		
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idConge")
	private long idConge;
	
	
	@JsonFormat(pattern="yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
	@Column (name="StartDateConge")
	private LocalDate StartDateConge;
	
	@JsonFormat(pattern="yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
	@Column (name="EndDateConge")
	private LocalDate EndDateConge;
	
	@Column (name="SoldeConge")
	private int SoldeConge;
	@Column (name="confirmation")
	private boolean confirmation;
	
	
	@ManyToOne
	private Employe EmployeConges;
	
	public int getSoldeConge() {
		return SoldeConge;
	}

	public void setSoldeConge(int soldeConge) {
		SoldeConge = soldeConge;
	}

	public boolean isConfirmation() {
		return confirmation;
	}

	public void setConfirmation(boolean confirmation) {
		this.confirmation = confirmation;
	}



	public long getIdConge() {
		return idConge;
	}

	public void setIdConge(long idConge) {
		this.idConge = idConge;
	}

	

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}




	public LocalDate getStartDateConge() {
		return StartDateConge;
	}

	public void setStartDateConge(LocalDate startDateConge) {
		StartDateConge = startDateConge;
	}

	public LocalDate getEndDateConge() {
		return EndDateConge;
	}

	public void setEndDateConge(LocalDate endDateConge) {
		EndDateConge = endDateConge;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name = "Description")
	private String Description;
	
	public Employe getEmployeConges() {
		return EmployeConges;
	}
	@JsonIgnore
	public void setEmployeConges(Employe employeConges) {
		EmployeConges = employeConges;
	}

	public Conge(long idConge, LocalDate startDateConge, LocalDate endDateConge, int soldeConge, boolean confirmation,
			Employe employeConges, String description) {
		super();
		this.idConge = idConge;
		StartDateConge = startDateConge;
		EndDateConge = endDateConge;
		SoldeConge = soldeConge;
		this.confirmation = confirmation;
		EmployeConges = employeConges;
		Description = description;
	}
	
}
