package banque.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import banque.entities.Credit;

public interface CreditRepo extends CrudRepository<Credit, Long>{
	
}
