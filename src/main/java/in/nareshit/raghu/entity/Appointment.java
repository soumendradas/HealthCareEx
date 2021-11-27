package in.nareshit.raghu.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.format.annotation.DateTimeFormat;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "appointment_tab",
		uniqueConstraints = @UniqueConstraint(columnNames = {"doctor_fk_col","app_date_col"}))
public class Appointment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "app_id_col")
	private Long id;
	
	@DateTimeFormat(pattern = "MM/dd/yyyy")
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
