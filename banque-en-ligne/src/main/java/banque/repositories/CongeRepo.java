package banque.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import banque.entities.Conge;


public interface CongeRepo extends CrudRepository<Conge, Long>{
	
	/*@Query("SELECT c FROM Conge c WHERE c.EmployeConges.IdEmploye= :IdEmploye")
	List<Conge> getCongeByEmploye(@Param("IdEmploye") Long employetid);*/
}
