package banque.services;


import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;


@Service
public class MailService {

	
	public void envoyerMailPersonnelDateExamen() throws  MailjetException{
		MailjetRequest request;
	    MailjetResponse response;
	    ClientOptions options = ClientOptions.builder()
	            .apiKey("4c86c9c22c9174bf41a6e5272fc867ee")
	            .apiSecretKey("d11d984c34f098c2be37c9de8a7c24df")
	            .build();
	      
	      MailjetClient client = new MailjetClient(options);
	  
	    try {
			request = new MailjetRequest(Emailv31.resource)
			.property(Emailv31.MESSAGES, new JSONArray()
			.put(new JSONObject()
			.put(Emailv31.Message.FROM, new JSONObject()
			.put("Email", "jm.lessous1@gmail.com")
			.put("Name", "JMLESSOUS"))
			.put(Emailv31.Message.TO, new JSONArray()
			.put(new JSONObject()
			.put("Email", "cyrinekenzabendebba@gmail.com")
			.put("Name", "bendebba")))
			.put(Emailv31.Message.SUBJECT, "Validation du retrait")
			.put(Emailv31.Message.TEXTPART, "Validation du retrait")
			.put(Emailv31.Message.HTMLPART, "<h4>   Bonjour Mme/M. </h4> "
					+ "<br/><p> Vous Venez de retirer un montant de 1000 Dinars de votre Compte<p> "
					+ "<br/><p>Merci De Ne pas Repondre<p> "
					+ "<br/> Si vous ne souhaitez plus recevoir d'emails de la part de JMlessous, vous pouvez vous <a>désinscrire</a>.")
			.put(Emailv31.Message.CUSTOMID, "Validation du Retrait")));
			response = client.post(request);
			System.out.println(response.getStatus());
		    System.out.println(response.getData());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    
	    
	  }

	

}
