package banque.repositories;

import org.springframework.data.repository.CrudRepository;

import banque.entities.CompteCourant;

public interface CompteCourantRepository extends CrudRepository<CompteCourant, Long> {

}
