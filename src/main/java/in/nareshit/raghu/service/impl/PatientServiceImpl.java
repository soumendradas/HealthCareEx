package in.nareshit.raghu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.nareshit.raghu.entity.Patient;
import in.nareshit.raghu.exception.PatientNotFoundException;
import in.nareshit.raghu.repository.PatientRepository;
import in.nareshit.raghu.service.IPatientService;

@Service
public class PatientServiceImpl implements IPatientService {
	
	@Autowired
	private PatientRepository repo;

	@Override
	public Long savePatient(Patient patient) {
		
		return repo.save(patient).getId();
	}

	@Override
	public List<Patient> getAllPatients() {
		
		return repo.findAll();
	}

	@Override
	public void removePatient(Long id) {
		repo.delete(getOnePatient(id));

	}

	@Override
	public Patient getOnePatient(Long id) {
		
		return repo.findById(id).orElseThrow(()->new PatientNotFoundException(id+" not found"));
	}

	@Override
	public void updatePatient(Patient patient) {
		if(repo.existsById(patient.getId())) {
			repo.save(patient);
		}else {
			throw new PatientNotFoundException(patient.getId()+" not found");
		}

	}

}
