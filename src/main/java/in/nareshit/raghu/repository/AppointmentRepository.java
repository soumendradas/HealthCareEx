package in.nareshit.raghu.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nareshit.raghu.entity.Appointment;
import in.nareshit.raghu.entity.Doctor;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	
	public List<Appointment> findByDate(LocalDate date);
	
	public List<Appointment> findByDoctor(Doctor doctor);
}
