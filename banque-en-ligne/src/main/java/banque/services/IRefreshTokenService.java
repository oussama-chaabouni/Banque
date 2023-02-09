package banque.services;
import java.util.Optional;

import banque.entities.RefreshToken;
import banque.security.UserPrincipal;
public interface IRefreshTokenService {
	
	Optional<RefreshToken> getByToken(String token);
	RefreshToken CreateRefreshToken(UserPrincipal principal);
	boolean VerifyExpiration(RefreshToken token) throws Exception;
	int DeleteByUser(String username);

}
