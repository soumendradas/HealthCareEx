package in.nareshit.raghu.service;

import java.util.List;
import java.util.Map;

import in.nareshit.raghu.entity.Doctor;

public interface IDoctorService {
	public Long saveDoctor(Doctor doc);
	public List<Doctor> getAllDoctors();
	public void removeDoctor(Long id);
	public Doctor getOneDoctor(Long id);
	public void updateDoctor(Doctor doc);
	
	public boolean isEmailExist(String email, Long id);
	public boolean isMobileExist(String mobile, Long id);
	
	public Map<Long, String> getDoctorIdNamesAndSpec();
}
