package in.nareshit.raghu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.raghu.entity.Doctor;
import in.nareshit.raghu.entity.Specialization;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	
	@Query("SELECT COUNT(email) from Doctor WHERE email = :email")
	Integer getEmailCount(String email);
	
	@Query("SELECT COUNT(email) from Doctor WHERE email = :email AND id != :id")
	Integer getEmailCountForEdit(String email, Long id);
	
	@Query("SELECT COUNT(mobile) from Doctor WHERE mobile = :mobile")
	Integer getMobileCount(String mobile);
	
	@Query("SELECT COUNT(mobile) from Doctor WHERE mobile = :mobile AND id != :id")
	Integer getMobileCountForEdit(String mobile, Long id);
	
	
	@Query("SELECT id, firstName, lastName, specialization FROM Doctor")
	List<Object[]> getDoctorIdNamesAndSpec();
	
	List<Doctor> findBySpecialization(Specialization specialization);
	
//	@Query("SELECT doc FROM Doctor doc INNER JOIN doc.specialization as spec WHERE spec.id = :specId")
//	List<Doctor> findBySpecId(Long specId);

}
