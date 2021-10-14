package in.nareshit.raghu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.nareshit.raghu.constants.UserRoles;
import in.nareshit.raghu.entity.Patient;
import in.nareshit.raghu.entity.User;
import in.nareshit.raghu.exception.PatientNotFoundException;
import in.nareshit.raghu.repository.PatientRepository;
import in.nareshit.raghu.service.IPatientService;
import in.nareshit.raghu.service.IUserService;
import in.nareshit.raghu.util.MyMailUtil;
import in.nareshit.raghu.util.UserUtil;

@Service
public class PatientServiceImpl implements IPatientService {
	
	@Autowired
	private PatientRepository repo;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private UserUtil userUtil;
	
	@Autowired
	private MyMailUtil mailUtil;

	@Override
	@Transactional
	public Long savePatient(Patient patient) {
		
		Long id = repo.save(patient).getId();
		
		if(id != null) {
			String pwd = userUtil.getPwd();
			User user = new User();
			user.setDisplayName(patient.getFirstName()+" "+patient.getLastName());
			user.setUsername(patient.getEmail());
			user.setPassword(pwd);
			user.setRole(UserRoles.PATIENT.name());
			Long gen_id = userService.saveUser(user);
			
			if(gen_id != null) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						String msg = "username "+patient.getEmail()+
								" and password is "+pwd;
						
						mailUtil.send(patient.getEmail(), "Patient id created", msg);
						
					}
				}).start();
			}
			
		}
		
		return id;
	}

	@Override
	@Transactional
	public List<Patient> getAllPatients() {
		
		return repo.findAll();
	}

	@Override
	@Transactional
	public void removePatient(Long id) {
		repo.delete(getOnePatient(id));

	}

	@Override
	@Transactional
	public Patient getOnePatient(Long id) {
		
		return repo.findById(id).orElseThrow(()->new PatientNotFoundException(id+" not found"));
	}

	@Override
	@Transactional
	public void updatePatient(Patient patient) {
		if(repo.existsById(patient.getId())) {
			repo.save(patient);
		}else {
			throw new PatientNotFoundException(patient.getId()+" not found");
		}

	}
	
	@Override
	public boolean isEmailExist(Long id, String email) {
		
		if(id != 0) {
			return repo.getEmailCountForEdit(id, email) > 0;
		}else {
			return repo.getEmailCount(email) > 0;
		}
		
	}

}
