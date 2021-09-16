package in.nareshit.raghu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.raghu.entity.Specialization;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
	
	@Query("select count(specCode) from Specialization where specCode = :specCode")
	Integer getSpecCodeCount(String specCode);
	
	@Query("select count(specName) from Specialization where specName = :specName")
	Integer getSpecNameCount(String specName);
}
