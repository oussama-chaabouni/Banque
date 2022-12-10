package banque.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import banque.entities.Assurance;
@Repository
public interface AssuranceRepo extends CrudRepository<Assurance, Long> {

}
