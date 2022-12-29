package banque.repositories;

import org.springframework.data.repository.CrudRepository;

import banque.entities.Employe;

public interface EmployeRepo  extends CrudRepository<Employe, Long>{

}
