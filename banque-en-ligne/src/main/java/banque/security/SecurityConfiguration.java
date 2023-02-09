package banque.security;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.RequiredTypes;
import org.aspectj.weaver.ast.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@EnableWebSecurity
public class SecurityConfiguration  {
	
	
	@Autowired
	private   UserDetailsService userDetailService;
	

	@Autowired
	DataSource dataSource;
	
	@Autowired
	private CustomAuthenticationFilter authenticationFilter;
	

	private static final Logger logger = LogManager.getLogger(SecurityConfiguration.class);

    @Autowired
    UserDetailsService userDetailsService;
	
	@Bean
	public JWTAuthenticationEntryPoint getAuthenticationEntryPoint() {
		return new JWTAuthenticationEntryPoint();
	}
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().
		authorizeRequests()
	/*	
		.antMatchers("http://127.0.0.1:5000/**").hasAnyAuthority("FINANCIER","CLIENT","ADMIN")
		.antMatchers("/Assurance/**").hasAnyAuthority("FINANCIER","CLIENT","ADMIN")
		.antMatchers("/Emplois/**").hasAnyAuthority("RH","ADMIN")
		.antMatchers("/obligation/**").hasAnyAuthority("FINANCIER","CLIENT","ADMIN")
		.antMatchers("/formation/**").hasAnyAuthority("FINANCIER","RH","ADMIN")
		.antMatchers("/echeance/**").hasAnyAuthority("FINANCIER","CLIENT","ADMIN")
		.antMatchers("/credit/**").hasAnyAuthority("FINANCIER","CLIENT","ADMIN")
		.antMatchers("/Conge/**").hasAnyAuthority("FINANCIER","RH","ADMIN")
		.antMatchers("/titre/**").hasAnyAuthority("FINANCIER","CLIENT","ADMIN")
		.antMatchers("/Assurance/**").hasAnyAuthority("FINANCIER","CLIENT","ADMIN")
		.antMatchers("/action/**").hasAnyAuthority("FINANCIER","CLIENT","ADMIN")
		.antMatchers("/payement/**").hasAnyAuthority("FINANCIER","CLIENT","ADMIN")
		.antMatchers("/pg/**").hasAnyAuthority("FINANCIER","CLIENT","ADMIN")
		.antMatchers("/reclamation/**").hasAnyAuthority("FINANCIER","CLIENT","ADMIN")
		.antMatchers("/salaire/**").hasAnyAuthority("FINANCIER","CLIENT","ADMIN","RH")
		.antMatchers("/transaction/**").hasAnyAuthority("FINANCIER","CLIENT","ADMIN")
*/
		.antMatchers("/auth/**").permitAll()
		.antMatchers("/**").permitAll()
		.anyRequest().authenticated();
		//.and().exceptionHandling().authenticationEntryPoint(getAuthenticationEntryPoint());
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		System.out.print("----******------------->"+UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(authenticationFilter,UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
		
		}

	
}
