package in.nareshit.raghu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.nareshit.raghu.constants.UserRoles;
import in.nareshit.raghu.entity.Doctor;
import in.nareshit.raghu.entity.User;
import in.nareshit.raghu.exception.DoctorNotFoundException;
import in.nareshit.raghu.repository.DoctorRepository;
import in.nareshit.raghu.service.IDoctorService;
import in.nareshit.raghu.service.ISpecializationService;
import in.nareshit.raghu.service.IUserService;
import in.nareshit.raghu.util.MyCollectionsUtil;
import in.nareshit.raghu.util.MyMailUtil;
import in.nareshit.raghu.util.UserUtil;

@Service
public class DoctorServiceImpl implements IDoctorService {
	
	@Autowired
	private DoctorRepository repo;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ISpecializationService specService;
	
	@Autowired
	private UserUtil userUtil;
	
	@Autowired
	private MyMailUtil mailUtil;

	@Override
	@Transactional
	public Long saveDoctor(Doctor doc) {
		
		Long id = repo.save(doc).getId();
		
		if(id != null) {
			String pwd = userUtil.getPwd();
			User user = new User();
			user.setDisplayName(doc.getFirstName()+" "+doc.getLastName());
			user.setUsername(doc.getEmail());
			user.setPassword(pwd);
			user.setRole(UserRoles.DOCTOR.name());
			Long gen_id = userService.saveUser(user);
			
			if(gen_id != null) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						String msg = "Doctor id Created. username is "+doc.getEmail()+
								" and password is "+ pwd;
						mailUtil.send(doc.getEmail(), "Doctor account creation", msg);
						
					}
				}).start();
			}
			
		}
		
		return id;
	}

	@Override
	public List<Doctor> getAllDoctors() {
		
		return repo.findAll();
	}

	@Override
	public void removeDoctor(Long id) {
		
		repo.delete(getOneDoctor(id));
	}

	@Override
	public Doctor getOneDoctor(Long id) {
		
		return repo.findById(id).orElseThrow(()->new DoctorNotFoundException(id +" not found"));
	}

	@Override
	public void updateDoctor(Doctor doc) {
		User user = userUtil.getUser();
		String oldEmail = getOneDoctor(doc.getId()).getEmail();
		
		if(repo.existsById(doc.getId())) {
			if(user.getRole().equals(UserRoles.ADMIN.name())) {
				if(!oldEmail.equals(doc.getEmail())) {
					userService.updateUserEmail(oldEmail, doc.getEmail());
					repo.save(doc);
				}else {
					repo.save(doc);
				}
			}else if(user.getUsername().equals(oldEmail)) {
				if(!oldEmail.equals(doc.getEmail())) {
					userService.updateUserEmail(oldEmail, doc.getEmail());
					repo.save(doc);
				}else {
					repo.save(doc);
				}
			}
			
			
		}else {
			throw new DoctorNotFoundException(doc.getId() + " not found");
		}
		
	}
	
	@Override
	public boolean isEmailExist(String email, Long id) {
		if(userService.isEmailExist(email)) {
			return true;
		}
		
		else if(id != 0) {
			return repo.getEmailCountForEdit(email, id)>0;
		}
		
		return repo.getEmailCount(email) > 0;
		
	}
	
	@Override
	public boolean isMobileExist(String mobile, Long id) {
		if(id != 0) {
			return repo.getMobileCountForEdit(mobile, id) > 0;
		}
		return repo.getMobileCount(mobile)>0;
	}
	
	@Override
	public Map<Long, String> getDoctorIdNamesAndSpec() {
		List<Object[]> doctors = repo.getDoctorIdNamesAndSpec();		
		return MyCollectionsUtil.convertToMapForDoctor(doctors);
	}
	
	@Override
	public List<Doctor> findDoctorBySpecId(Long SpecId) {
		
		
		return repo.findBySpecialization(specService.getOneSpecialization(SpecId));
		
	}
	
	
	@Override
	public Doctor findDoctorByEmail(String email) {
		
		return repo.findByEmail(email)
				.orElseThrow(()->new DoctorNotFoundException(email+" Not Found"));
	}
	
	@Override
	public Long getAllDoctorsCount() {
		
		return repo.count();
	}

}
