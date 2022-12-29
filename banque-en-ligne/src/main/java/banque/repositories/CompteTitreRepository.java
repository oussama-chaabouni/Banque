package banque.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import banque.entities.CompteTitre;

@Repository
public interface CompteTitreRepository extends CrudRepository <CompteTitre,Long> {

}
