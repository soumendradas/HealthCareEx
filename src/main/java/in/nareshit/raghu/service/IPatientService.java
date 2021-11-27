package in.nareshit.raghu.service;

import java.util.List;

import in.nareshit.raghu.entity.Patient;

public interface IPatientService {
	
	public Long savePatient(Patient patient);
	public List<Patient> getAllPatients();
	public void removePatient(Long id);
	public Patient getOnePatient(Long id);
	public void updatePatient(Patient patient);
	public Patient getOnePatientByEmail(String email);
	
	public boolean isEmailExist(Long id, String email);
	
	Long getAllPatientCount();
	

}
