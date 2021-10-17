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
			.mvcMatchers("/patient/edit","patient/update")
				.hasAnyAuthority(UserRoles.PATIENT.name(), UserRoles.ADMIN.name())
			.mvcMatchers("/doctor/register", "/doctor/delete", "/doctor/save")
				.hasAuthority(UserRoles.ADMIN.name())
			
			.mvcMatchers("/doctor/edit", "doctor/update")
					.hasAnyAuthority(UserRoles.ADMIN.name(), UserRoles.DOCTOR.name())
			
			.mvcMatchers("/spec/**").hasAuthority(UserRoles.ADMIN.name())
			.mvcMatchers("appointment/register", "appointment/update", "appointment/delete")
					.hasAuthority(UserRoles.ADMIN.name())
			.mvcMatchers("appointment/all").permitAll()		
			.anyRequest().authenticated()
			
			.and()
			.formLogin()
			.defaultSuccessUrl("/spec/all", true)
			.and()
			.logout();
	}

}
