package in.nareshit.raghu.service;

import java.util.Optional;

import in.nareshit.raghu.entity.User;

public interface IUserService {
	
	Long saveUser(User user);
	Optional<User> findByUsername(String username);
	
	String updatePassword(String currentPassword, String newPassword);
	
	void updateUserEmail(String oldEmail, String newEmail);
	
	boolean isEmailExist(String email);
	
	String forgotPassword(String username);
	

}
