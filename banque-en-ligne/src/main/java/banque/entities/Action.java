package banque.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity	
@Table(name="Action")
public class Action implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idAction")
	private long idAction;
	
	@Column(name = "Symbole")
	private String Symbole;
	
	@Column(name = "High")
	private BigDecimal High;
	
	@Column(name = "Low")
	private BigDecimal Low;
	
	@Column(name = "Volume")
	private Long Volume;
	
	@Column(name = "Close")
	private BigDecimal Close;
	
	@Column(name = "Capital")
	private BigDecimal Capital;
	
	@Column(name = "ValeurActuelle")
	private BigDecimal ValeurActuelle;
	
	@ManyToOne
	private CompteTitre TitreActions;

	public long getIdAction() {
		return idAction;
	}

	public void setIdAction(long idAction) {
		this.idAction = idAction;
	}

	public String getSymbole() {
		return Symbole;
	}

	public void setSymbole(String symbole) {
		Symbole = symbole;
	}

	public BigDecimal getHigh() {
		return High;
	}

	public void setHigh(BigDecimal high) {
		High = high;
	}

	public BigDecimal getLow() {
		return Low;
	}

	public void setLow(BigDecimal low) {
		Low = low;
	}

	public Long getVolume() {
		return Volume;
	}

	public void setVolume(Long volume) {
		Volume = volume;
	}

	public BigDecimal getClose() {
		return Close;
	}

	public void setClose(BigDecimal close) {
		Close = close;
	}
	
	public BigDecimal getCapital() {
		return Capital;
	}

	public void setCapital(BigDecimal capital) {
		Capital = capital;
	}

	public CompteTitre getTitreActions() {
		return TitreActions;
	}

	public void setTitreActions(CompteTitre titreActions) {
		TitreActions = titreActions;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public BigDecimal getValeurActuelle() {
		return ValeurActuelle;
	}

	public void setValeurActuelle(BigDecimal valeurActuelle) {
		ValeurActuelle = valeurActuelle;
	}

	public Action() {
		super();
	}

	public Action(String symbole, BigDecimal capital) {
		super();
		Symbole = symbole;
		Capital = capital;
	}

	public Action(String symbole, BigDecimal capital, CompteTitre titreActions) {
		super();
		Symbole = symbole;
		Capital = capital;
		TitreActions = titreActions;
	}
	
}
