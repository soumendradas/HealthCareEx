package in.nareshit.raghu.service;

import java.util.List;

import in.nareshit.raghu.entity.Specialization;

public interface ISpecializationService {
	
	public Long saveSpecialization(Specialization spec);
	public List<Specialization> getAllSpecializations();
	public void removeSpecialization(Long id);
	public Specialization getOneSpecialization(Long id);
	public String updateSpecialization(Specialization spec);
	
	public boolean isSpecCodeExist(String specCode, Long id);
	public boolean isSpecNameExist(String specName, Long id);

}
