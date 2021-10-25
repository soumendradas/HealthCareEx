package in.nareshit.raghu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import in.nareshit.raghu.constants.UserRoles;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
			.mvcMatchers("/patient/register", "/patient/save").permitAll()
			.mvcMatchers("/patient/all","/patient/delete")
				.hasAuthority(UserRoles.ADMIN.name())
			.mvcMatchers("/patient/**")
				.hasAnyAuthority(UserRoles.PATIENT.name(), UserRoles.ADMIN.name())
			.mvcMatchers("/doctor/**")
				.hasAuthority(UserRoles.ADMIN.name())
			
			.mvcMatchers("/spec/**").hasAuthority(UserRoles.ADMIN.name())
			.mvcMatchers("appointment/register", "appointment/update", "appointment/delete")
					.hasAuthority(UserRoles.ADMIN.name())
			.mvcMatchers("appointment/view", "appointment/viewAll")
					.hasAuthority(UserRoles.PATIENT.name())
			.mvcMatchers("appointment/all").permitAll()		
			.anyRequest().authenticated()
			
			.and()
			.formLogin()
			.defaultSuccessUrl("/spec/all", true)
			.and()
			.logout();
	}

}
