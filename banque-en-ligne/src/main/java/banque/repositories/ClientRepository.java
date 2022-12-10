package banque.repositories;

import org.springframework.data.repository.CrudRepository;

import banque.entities.Client;

public interface ClientRepository extends CrudRepository<Client, Long>  {

}
