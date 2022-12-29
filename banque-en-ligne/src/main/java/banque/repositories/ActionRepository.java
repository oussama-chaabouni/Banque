package banque.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import banque.entities.Action;

@Repository
public interface ActionRepository extends CrudRepository <Action,Long>{

}
