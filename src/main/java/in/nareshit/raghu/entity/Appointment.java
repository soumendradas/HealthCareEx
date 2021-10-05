package in.nareshit.raghu.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "app_id_col")
	private Long id;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name="app_date_col")
	private LocalDate date;
	
	@Column(name = "app_no_of_slots_col")
	private Integer noOfSlots;
	
	@Column(name="app_details_col")
	private String details;
	
	@Column(name="app_fee_col")
	private Double fee;
	
	@ManyToOne
	@JoinColumn(name = "doctor_fk_col")
	private Doctor doctor;

}
