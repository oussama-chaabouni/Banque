package banque.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import banque.entities.Client;

@Repository
public interface ClientRepository extends CrudRepository <Client,Long> {
	@Query("select p.Email from Client p")
	List<String> getAllEmails();
}
