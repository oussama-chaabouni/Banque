package banque.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import banque.entities.Formation;

@Transactional
@Repository
public interface FormationRepository extends JpaRepository<Formation, Long>{

	
	
}