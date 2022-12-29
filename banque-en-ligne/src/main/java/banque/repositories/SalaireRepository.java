package banque.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import banque.entities.Salaire;

@Transactional
@Repository
public interface SalaireRepository extends JpaRepository<Salaire, Long>{
	
	

}
