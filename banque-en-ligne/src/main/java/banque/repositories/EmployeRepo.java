package banque.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import banque.entities.Employe;
@Repository
public interface EmployeRepo  extends CrudRepository<Employe, Long>{
	@Query("select p.email from Employe p")
	List<String> getAllEmailsE();
	Employe findByEmail(String Email);
}
