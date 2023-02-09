package banque.security;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import banque.entities.Client;
import banque.entities.Employe;
public class UserPrincipal implements UserDetails {
	
	private Object user;
	public Employe e;
	public UserPrincipal(Object user) {
		this.user = user;
	}
	
	private String getTypeUser() {
		
		if(user instanceof Client) 
			return "CLIENT";
	    else if (user instanceof Employe) {
	        Employe employe = (Employe) user;
	        switch (employe.getRole()) {
	            case RH:
	                return "RH";
	            case FINANCIER:
	                return "FINANCIER";
	            case ADMIN:
	                return "ADMIN";
	            default:
	                return "";
	        }
	    }
	    return "";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities=new ArrayList<>();
		System.out.println("ttttttttttttttttttttttttttt"+getTypeUser());
		if(getTypeUser().equals("CLIENT")) {
			authorities.add(new SimpleGrantedAuthority("CLIENT"));
		}
		else if(getTypeUser().equals("RH")) {
			authorities.add(new SimpleGrantedAuthority("RH"));
			
		}
		else if(getTypeUser().equals("FINANCIER")) {
			System.out.print("FINANCIER");
			authorities.add(new SimpleGrantedAuthority("FINANCIER"));
		}
		else if(getTypeUser().equals("ADMIN")){
			authorities.add(new SimpleGrantedAuthority("ADMIN"));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		if(getTypeUser().equals("CLIENT")) {
			Client A=(Client)user;
			return A.getPassword();
		}
		else if(getTypeUser().equals("RH")) {
			Employe C=(Employe)user;
			return C.getPassword();
		}
		else if(getTypeUser().equals("FINANCIER")) {
			Employe M=(Employe)user;
			return M.getPassword();
		}
		else if(getTypeUser().equals("ADMIN")) {
			Employe I=(Employe)user;
			return I.getPassword();
		}
		else{
			return "";
		}
		
	}
/*
	@Override
	public String getUsername() {
		
		
		if(getTypeUser().equals("agent")) {
			Agent A=(Agent)user;
			
			return A.getUserName();
		}
		else if(getTypeUser().equals("customer")) {
			Customer C=(Customer)user;
			
			return C.getUserName();
		}
		else if(getTypeUser().equals("manager")) {
			Manager M=(Manager)user;
			return M.getUserName();
		}
		else if(getTypeUser().equals("investor")) {
			Investor I=(Investor)user;
			return I.getUserName();
		}
		else{
			return "";
		}
		
	}
*/
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {

		if(getTypeUser().equals("CLIENT")) {
			Client A=(Client)user;
			return A.isEnabled();
		
	}
	else if(getTypeUser().equals("RH")) {
		Employe C=(Employe)user;
		return C.isEnabled();
	}
	else if(getTypeUser().equals("FINANCIER")) {
		Employe M=(Employe)user;
		return M.isEnabled();
	}
	else if(getTypeUser().equals("ADMIN")) {
		Employe I=(Employe)user;
		return I.isEnabled();
	}
		else{
			return false;
		}
		
	}


	@Override
	public String getUsername() {
		if(getTypeUser().equals("CLIENT")) {
			Client A=(Client)user;
			return A.getEmail();
		}
		else if(getTypeUser().equals("RH")) {
			Employe C=(Employe)user;
			return C.getEmail();
		}
		else if(getTypeUser().equals("FINANCIER")) {
			Employe M=(Employe)user;
			return M.getEmail();
		}
		else if(getTypeUser().equals("ADMIN")) {
			Employe I=(Employe)user;
			return I.getEmail();
		}
		else{
			return "";
		}
	}


}
