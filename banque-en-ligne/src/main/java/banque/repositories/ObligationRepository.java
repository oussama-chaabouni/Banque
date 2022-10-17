package banque.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import banque.entities.Obligation;

@Repository
public interface ObligationRepository extends CrudRepository<Obligation,Long> {
}
