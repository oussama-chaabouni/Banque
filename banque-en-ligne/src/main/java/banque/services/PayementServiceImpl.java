package banque.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import banque.entities.Payement;
import banque.repositories.PayementRepository;

@Service
public class PayementServiceImpl implements PayementService {
	
	@Autowired
	PayementRepository PayementRep;
		
	@Override
		public List<Payement> retrieveAllPayements() {
			List<Payement> transactions= (List<Payement>) (PayementRep.findAll());
			//traja3ali de type iterable , donc nrodha traja3 type transaction
			return transactions;
		}

		@Override
		public Payement retrievePayement(Long id) {
			return PayementRep.findById(id).get();
		}

		@Override
		public Payement addPayement(Payement p) {
			return PayementRep.save(p);
		}
		

		@Override
		public void deletePayement(Long id) {
			PayementRep.deleteById(id);

		}

		@Override
		public Payement updatePayement(Payement p) {
			return PayementRep.save(p);
		}
		
}
		
