package in.nareshit.raghu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.raghu.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username);
	
	@Query("SELECT COUNT(username) FROM User WHERE username= :username")
	Long getUsernameCount(String username);
	
	@Modifying
	@Query("UPDATE User SET password= :password WHERE id = :id")
	void updatePassword(Long id, String password);

}
