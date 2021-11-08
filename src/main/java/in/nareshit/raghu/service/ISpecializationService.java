package in.nareshit.raghu.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import in.nareshit.raghu.entity.Specialization;

public interface ISpecializationService {
	
	public Long saveSpecialization(Specialization spec);
	public List<Specialization> getAllSpecializations();
	public void removeSpecialization(Long id);
	public Specialization getOneSpecialization(Long id);
	public String updateSpecialization(Specialization spec);
	
	public Page<Specialization> getAllSpecializtions(Pageable pageable);	
	public boolean isSpecCodeExist(String specCode, Long id);
	public boolean isSpecNameExist(String specName, Long id);
	
	public Map<Long, String> getSpecIdAndName();
	
	Long getAllSpecializationCount();

}
