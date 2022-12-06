package banque.controller;

import java.math.BigInteger;
import java.time.LocalDateTime;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import banque.entities.OrderRequest;
import banque.entities.OrderResponse;
import banque.repositories.PayementRepository;
import banque.repositories.TransactionRepository;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/pg")
public class RazorpayController {

	private RazorpayClient client;
	PayementRepository payementRep;
	LocalDateTime currentDateTime = LocalDateTime.now();

	
	//esprit.tn
	private static final String SECRET_ID1 = "rzp_test_6Z9v5BeeQjLseE";
	private static final String SECRET_KEY1 = "kVhoUMfrmz8TMavHDmi7IhQQ";
	//gmail.com
	private static final String SECRET_ID2 = "rzp_test_gYpXBLcFwq202P";
	private static final String SECRET_KEY2 = "MK5DBYLf69zImLRpVZ4R9ajL";

	@RequestMapping(path = "/createOrder", method = RequestMethod.POST)
	public OrderResponse createOrder(@RequestBody OrderRequest orderRequest) {
		OrderResponse response = new OrderResponse();
		try {

			if (orderRequest.getAmount().intValue() > 1000) {
				client = new RazorpayClient(SECRET_ID1, SECRET_KEY1);
			} else {
				client = new RazorpayClient(SECRET_ID2, SECRET_KEY2);
			}

			Order order = createRazorPayOrder(orderRequest.getAmount());
			System.out.println("---------------------------");
			String orderId = (String) order.get("id");
			System.out.println("Order ID: " + orderId);
			System.out.println("---------------------------");
			response.setRazorpayOrderId(orderId);
			response.setApplicationFee("" + orderRequest.getAmount());
			if (orderRequest.getAmount().intValue() > 1000) {
				response.setSecretKey(SECRET_KEY1);
				response.setSecretId(SECRET_ID1);
				response.setPgName("razor1");
				//float am= 0.12f;
				//String beneficiaire=orderRequest.getCustomerName() ;
				//payementRep.ajouterPayement(1233333333,beneficiaire,"1234444444" ,am, "paiement dar","Paiement avec Succés","Paiement Effectué Avec Succés", currentDateTime);

			} else {
				response.setSecretKey(SECRET_KEY2);
				response.setSecretId(SECRET_ID2);
				response.setPgName("razor2");
				
				//long rib= orderRequest.getRib();
				//String customerName=orderRequest.getCustomerName();
				//String customerRib=orderRequest.getCustomerRib();
				//float montantAPayer = orderRequest.getAmount().floatValue();
				//String motif= orderRequest.getMotif();
				//payementRep.ajouterPayement(rib,customerName,customerRib,montantAPayer, motif,"Paiement avec Succés","Paiement Effectué Avec Succés", currentDateTime);

			}
			
			return response;
		} catch (RazorpayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;

	}

	private Order createRazorPayOrder(BigInteger amount) throws RazorpayException {

		JSONObject options = new JSONObject();
		options.put("amount", amount.multiply(new BigInteger("100")));
		options.put("currency", "INR");
		options.put("receipt", "txn_123456");
		options.put("payment_capture", 1); // You can enable this if you want to do Auto Capture.
		return client.orders.create(options);
	}
}