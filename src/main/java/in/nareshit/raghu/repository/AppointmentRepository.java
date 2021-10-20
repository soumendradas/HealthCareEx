package in.nareshit.raghu.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.raghu.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	
	public List<Appointment> findByDate(LocalDate date);
	
	//public List<Appointment> findByDoctor(Doctor doctor);
	
	@Query("SELECT app.id, app.date, app.noOfSlots, app.fee FROM Appointment app INNER JOIN app.doctor as doc WHERE doc.id= :docId and app.date >= :date")
	public List<Object[]> findByDoctorIdAndDate(Long docId, LocalDate date);
}
