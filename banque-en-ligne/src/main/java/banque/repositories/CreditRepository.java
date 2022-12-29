package banque.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import banque.entities.Credit;
@Repository
public interface CreditRepository extends CrudRepository<Credit, Long>{

}
