package in.nareshit.raghu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.raghu.entity.Specialization;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
	
	@Query("select count(specCode) from Specialization where specCode = :specCode")
	Integer getSpecCodeCount(String specCode);
	
	@Query("select count(specCode) from Specialization where specCode = :specCode and id != :id")
	Integer getSpecCodeCountForEdit(String specCode, Long id);
	
	@Query("select count(specName) from Specialization where specName = :specName")
	Integer getSpecNameCount(String specName);
	
	@Query("SELECT COUNT(specName) FROM Specialization WHERE specName = :specName AND id != :id")
	Integer getSpecNameCountForEdit(String specName, Long id);
	
	@Query("SELECT id, specName FROM Specialization")
	List<Object[]> getSpecIdAndName();
}
