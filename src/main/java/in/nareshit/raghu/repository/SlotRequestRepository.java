package in.nareshit.raghu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.raghu.entity.SlotRequest;

public interface SlotRequestRepository extends JpaRepository<SlotRequest, Long> {

	@Modifying
	@Query("UPDATE SlotRequest SET status=:status WHERE id=:id")
	void updateSlotRequestStatus(Long id, String status);
	
	@Query("SELECT sl FROM SlotRequest as sl INNER JOIN sl.patient as pat WHERE pat.email=:patientEmail")
	List<SlotRequest> getAllPatientSlots(String patientEmail);
	
	@Query("SELECT app.id FROM SlotRequest as sl INNER JOIN sl.appointment as app WHERE sl.id = :slotId")
	Long getAppointmentId(Long slotId);
	
	@Query("SELECT sl FROM SlotRequest as sl INNER JOIN sl.appointment.doctor as doc WHERE doc.email = :doctorMail")
	List<SlotRequest> getAllDoctorSlots(String doctorMail);
}
