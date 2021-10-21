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
import in.nareshit.raghu.util.MyMailUtil;
import in.nareshit.raghu.util.UserUtil;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

	@Autowired
	private UserRepository repo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserUtil userUtil;

	@Autowired
	private MyMailUtil mailUtil;

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
	public void updatePassword(String currentPassword, String newPassword) {
		String username = userUtil.getLoginUsername();
		User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username not found"));

		if (passwordEncoder.matches(currentPassword, user.getPassword())) {
			user.setPassword(newPassword);
			saveUser(user);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					mailUtil.send(username,
							"Password Changed",
							"You recently change your password");
					
				}
			}).start();
		
		}

	}

	@Override
	public void updateUserEmail(String oldEmail, String newEmail) {

		User user = findByUsername(oldEmail).orElseThrow(() -> new UsernameNotFoundException("username not found"));

		if (!isEmailExist(newEmail)) {

			user.setUsername(newEmail);
			repo.save(user);

			new Thread(new Runnable() {

				@Override
				public void run() {
					mailUtil.send(oldEmail, "Email id changed", "Email id changed to " + newEmail);

				}
			}).start();
		}else {
			throw new UsernameNotFoundException("New Email is Exist...... please try again");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> opt = findByUsername(username);

		if (!opt.isPresent()) {
			throw new UsernameNotFoundException(username + " is invalid or wrong");
		} else {
			User user = opt.get();

			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
					Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
		}

	}

	@Override
	public boolean isEmailExist(String email) {

		return repo.getUsernameCount(email) > 0;
	}

}
