package banque.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import banque.entities.Employe;

@Transactional
@Repository
public interface EmployeeRepository extends JpaRepository<Employe, Long>{

	
	
}
