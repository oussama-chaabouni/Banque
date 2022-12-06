package banque.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter

@Entity	
@Table(name="Salaire")
public class Salaire implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idsalaire")
	private long idsalaire;
	
	@Column(name = "nomemploye")
	private String nomemploye;
	
	@Column(name = "salaire")
	private float salaire;
	
	@Column(name = "salairenet")
	private float salairenet;
	
	@Column(name = "nbheuresup")
	private float nbheuresup;
	
	@Column(name = "prixheuresup")
	private float prixheuresup;
	
	@Column(name = "totaltax")
	private float totaltax;
	
	@JsonIgnore
	@OneToOne(mappedBy = "salaire")
    private Employe employe;
	
}
