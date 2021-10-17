package in.nareshit.raghu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.raghu.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
	
	
	@Query("SELECT COUNT(email) FROM Patient WHERE email = :email")
	Integer getEmailCount(String email);
	
	@Query("SELECT COUNT(email) FROM Patient WHERE email = :email AND id != :id")
	Integer getEmailCountForEdit(Long id, String email);
	
	Optional<Patient> findByEmail(String email);

}
