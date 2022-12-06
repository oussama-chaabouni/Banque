package banque.entities;

import java.math.BigInteger;
import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;

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
public class OrderRequest {
	
	long rib; //zedet hedhi
	String customerName; //beneficiaire
	long customerRib;
	BigInteger amount;
	String motif; //w hedhi
	
	//kenou hakka:
	/*
	String customerName;
	String email;
	String phoneNumber;
	BigInteger amount;*/
}
