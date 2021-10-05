package in.nareshit.raghu.service;

import java.util.List;

import in.nareshit.raghu.entity.Appointment;

public interface IAppointmentService {
	
	public Long saveAppointment(Appointment appointment);
	public List<Appointment> getAllAppointment();
	public void removeAppointment(Long id);
	public Appointment getOneAppointment(Long id);
	public void updateAppointment(Appointment appointment);

}
