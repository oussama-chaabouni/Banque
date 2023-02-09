package banque.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import banque.entities.Client;
import banque.entities.Employe;
import banque.repositories.ClientRepository;
import banque.repositories.EmployeRepo;
import banque.services.ClientServiceImpl;

@Component
class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ClientServiceImpl accountService;
    @Autowired
    private ClientRepository accountRepo;
    @Autowired
    private EmployeRepo account2Repo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	
        Client client = accountRepo.findByEmail(email);
        if (client != null) {
    		
            return new UserPrincipal(client);
        }
        Employe employe = account2Repo.findByEmail(email);
        if (employe != null) {
        	System.out.print("----gfgfgfgf******------------->"+UsernamePasswordAuthenticationFilter.class);
            return new UserPrincipal(employe);
        };
        throw new UsernameNotFoundException("User not found with email: " + email);
    
    }

    // ...
}
