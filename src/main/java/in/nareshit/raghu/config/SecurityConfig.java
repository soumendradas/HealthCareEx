package in.nareshit.raghu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
			.mvcMatchers("/myjs/**","myRes/**").permitAll()		//for enable javascript file
			.mvcMatchers("/user/login", "/user/forgotPass","/user/genNewPass").permitAll()
			.mvcMatchers("/patient/register", "/patient/save","patient/checkEmail").permitAll()
			.mvcMatchers("/patient/all","/patient/delete")
				.hasAuthority(UserRoles.ADMIN.name())
			.mvcMatchers("/patient/**")
				.hasAnyAuthority(UserRoles.PATIENT.name(), UserRoles.ADMIN.name())
			
			.mvcMatchers("/doctor/showProfile")
				.hasAuthority(UserRoles.DOCTOR.name())
			.mvcMatchers("/doctor/**")
				.hasAuthority(UserRoles.ADMIN.name())
			
			.mvcMatchers("/spec/**").hasAuthority(UserRoles.ADMIN.name())
			.mvcMatchers("appointment/view", "appointment/viewSlot")
					.hasAuthority(UserRoles.PATIENT.name())
			.mvcMatchers("appointment/currentDoc")
				.hasAuthority(UserRoles.DOCTOR.name())	
			.mvcMatchers("appointment/**").hasAuthority(UserRoles.ADMIN.name())
			.mvcMatchers("/slots/book","/slots/patient", "/slots/cancel", "/slots/invoice")
				.hasAuthority(UserRoles.PATIENT.name())
			.mvcMatchers("/slots/doctor").hasAuthority(UserRoles.DOCTOR.name())
			.mvcMatchers("/slots/**").hasAuthority(UserRoles.ADMIN.name())
			.anyRequest().authenticated()
			
			.and()
			.formLogin()
			.loginPage("/user/login")  //show login Page
			.loginProcessingUrl("/login")	//POST (do login)
			.defaultSuccessUrl("/user/setup", true)
			.failureUrl("/user/login?error=true")	//if login is failed
			.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))  //Url for logout
			.logoutSuccessUrl("/user/login?logout=true")		//on logout success
			;
	}

}
