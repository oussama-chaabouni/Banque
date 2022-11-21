package banque.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import banque.entities.Transaction;
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
@Table(name="Reclamation")
public class Reclamation implements Serializable{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idReclamation;
	private long rib;
	@Enumerated(EnumType.STRING)
    private TypeTransaction typeTransaction;
	private float montant;
	private String motif;
	private String statut;
	private String codeRaison;
	private LocalDateTime dateOperation;
	private float solde;
	/*
	@JsonBackReference
	@ManyToOne
	Transaction transaction;*/
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTransaction")
    private Transaction transaction;
	
	
	
	
	
	
}