package banque.entities;

import java.math.BigInteger;

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
public class OrderResponse {

	String secretKey;
	String razorpayOrderId;
	String applicationFee;
	String secretId;
	String pgName;
}
