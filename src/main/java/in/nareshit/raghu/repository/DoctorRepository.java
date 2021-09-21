package in.nareshit.raghu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.raghu.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	
	@Query("SELECT COUNT(email) from Doctor WHERE email = :email")
	Integer getEmailCount(String email);
	
	@Query("SELECT COUNT(email) from Doctor WHERE email = :email AND id != :id")
	Integer getEmailCountForEdit(String email, Long id);
	
	@Query("SELECT COUNT(mobile) from Doctor WHERE mobile = :mobile")
	Integer getMobileCount(String mobile);
	
	@Query("SELECT COUNT(mobile) from Doctor WHERE mobile = :mobile AND id != :id")
	Integer getMobileCountForEdit(String mobile, Long id);

}
