package in.nareshit.raghu.service.impl;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.nareshit.raghu.entity.Appointment;
import in.nareshit.raghu.exception.AppointmentNotFoundException;
import in.nareshit.raghu.repository.AppointmentRepository;
import in.nareshit.raghu.service.IAppointmentService;

@Service
public class AppointmentServiceImpl implements IAppointmentService {
	
	@Autowired
	private AppointmentRepository repo;

	@Override
	public Long saveAppointment(Appointment appointment) {
		
		return repo.save(appointment).getId();
	}

	@Override
	public List<Appointment> getAllAppointment() {
		
		return repo.findAll();
	}

	@Override
	public void removeAppointment(Long id) {
		repo.delete(getOneAppointment(id));

	}

	@Override
	public Appointment getOneAppointment(Long id) {
		
		return repo.findById(id).orElseThrow(()->
						new AppointmentNotFoundException("Id not found"));
	}

	@Override
	public void updateAppointment(Appointment appointment) {
		
		if(repo.existsById(appointment.getId())) {
			repo.save(appointment);
		}else {
			throw new AppointmentNotFoundException("Id is invalid and rejected");
		}

	}
	
	@Override
	public List<Appointment> getAppointmentByDate(LocalDate date){
		
		return repo.findByDate(date);
	}
	
	@Override
	public List<Object[]> getAppointmentByDoctor(Long docId, LocalDate date) {
		
		return repo.findByDoctorIdAndDate(docId);
	}
	
	@Override
	public List<Object[]> getAppointmentByDoctorEmail(String username) {
		
		return repo.findByDoctorEmail(username);
	}
	
	@Transactional
	@Override
	public void updateAppointmentSlot(Long appointment_id, int count) {
		
		repo.updateAppointmentSlot(appointment_id, count);
	}
	
	@Override
	public Long getAllSpecializationCount() {
		
		return repo.count();
	}

}
