package banque.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import banque.entities.Echeance;
@Repository
public interface EcheanceRepository extends CrudRepository<Echeance, Long>{

}
