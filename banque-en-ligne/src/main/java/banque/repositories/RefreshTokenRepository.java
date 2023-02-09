package banque.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import banque.entities.RefreshToken;
@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
	
	
	Optional<RefreshToken> findByToken(String token);
	
	@Query("delete from RefreshToken where agent_token_ida=?1")
	int deleteByAgent(Long Idag);

	@Query("delete from RefreshToken where customer_token_idc=?1")
	int deleteByCustomer(Long Idcustomer);
	

	@Query("delete from RefreshToken where manager_token_idm=?1")
	int deleteByManager(Long Idmanager);

	@Query("delete from RefreshToken where investor_token_id_investor=?1")
	int deleteByInvestor(long idInvestor);
	

}
