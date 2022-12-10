package banque.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import banque.entities.Conge;
import banque.entities.Emplois;
import banque.repositories.CongeRepo;
import banque.repositories.EmploisRepo;

@Service
public class EmploisService implements IEmploisService {
	@Autowired
	EmploisRepo er;
	
		@Override
		public List<Emplois> retrieveAllEmplois() {
			return (List<Emplois>) er.findAll();
		}

		@Override
		
		public Emplois addEmplois(Emplois e) {
			return er.save(e);
		}

		@Override
		public void deleteEmplois(Long id) {
			 er.deleteById(id);
			
		}

		@Override
		public Emplois updateEmplois(Emplois e) {
			er.save(e);
			return e;
		}

		@Override
		public Emplois retrieveEmploisById(Long id) {
			er.findById(id);
			return er.findById(id).orElse(null);
		}

	

	}


