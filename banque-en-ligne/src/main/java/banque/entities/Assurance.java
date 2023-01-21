package banque.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Entity	
@Table(name="Assurance")
public class Assurance implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idAssurance")
	private long idAssurance;
	
	@Column(name = "Description")
	private String Description;
	
	@Column(name = "Nom")
	private String Nom;

	

	@Column(name = "TypeAssurance")
	@Enumerated(EnumType.STRING)
	private TypeAssurance typeAssurance;
	
	@Column(name = "TypeCompte")
	@Enumerated(EnumType.STRING)
	private TypeCompte typeCompte;
	
	
	private boolean suggestion;


	public Assurance() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Assurance(long idAssurance, String description, String nom, TypeAssurance typeAssurance,
			TypeCompte typeCompte, boolean suggestion) {
		super();
		this.idAssurance = idAssurance;
		Description = description;
		Nom = nom;
		this.typeAssurance = typeAssurance;
		this.typeCompte = typeCompte;
		this.suggestion = suggestion;
	}


	public long getIdAssurance() {
		return idAssurance;
	}


	public void setIdAssurance(long idAssurance) {
		this.idAssurance = idAssurance;
	}


	public String getDescription() {
		return Description;
	}


	public void setDescription(String description) {
		Description = description;
	}


	public String getNom() {
		return Nom;
	}


	public void setNom(String nom) {
		Nom = nom;
	}


	public TypeAssurance getTypeAssurance() {
		return typeAssurance;
	}


	public void setTypeAssurance(TypeAssurance typeAssurance) {
		this.typeAssurance = typeAssurance;
	}


	public TypeCompte getTypeCompte() {
		return typeCompte;
	}


	public void setTypeCompte(TypeCompte typeCompte) {
		this.typeCompte = typeCompte;
	}


	public boolean isSuggestion() {
		return suggestion;
	}


	public void setSuggestion(boolean suggestion) {
		this.suggestion = suggestion;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	
	

}
