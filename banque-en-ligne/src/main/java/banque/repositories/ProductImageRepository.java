package banque.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import banque.entities.Client;


public interface ProductImageRepository extends JpaRepository<Client, Long> {
	
	//Optional<Client> findByName(String fileName);
}
