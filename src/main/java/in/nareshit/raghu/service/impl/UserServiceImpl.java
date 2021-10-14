package in.nareshit.raghu.service.impl;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import in.nareshit.raghu.entity.User;
import in.nareshit.raghu.repository.UserRepository;
import in.nareshit.raghu.service.IUserService;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public Long saveUser(User user) {
		
		String pwd = user.getPassword();
		
		String en_pwd = passwordEncoder.encode(pwd);
		user.setPassword(en_pwd);
		
		return repo.save(user).getId();
	}

	@Override
	public Optional<User> findByUsername(String username) {
		
		return repo.findByUsername(username);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> opt = findByUsername(username);
		
		if(!opt.isPresent()) {
			throw new UsernameNotFoundException(username+" is invalid or wrong");
		}else {
			User user = opt.get();
			
			return new org.springframework.security.core.userdetails.User(
					user.getUsername(),
					user.getPassword(),
					Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
					);
		}
		
	}

}
