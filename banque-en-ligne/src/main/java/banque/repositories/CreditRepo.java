package banque.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import banque.entities.Credit;
@Repository
public interface CreditRepo extends CrudRepository<Credit, Long>{
	
}
