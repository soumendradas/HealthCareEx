package in.nareshit.raghu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table(name = "slot_req_tab",
		uniqueConstraints = @UniqueConstraint(columnNames = {"app_id_fk_col","pat_id_fk_col"}))
public class SlotRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "app_id_fk_col")
	private Appointment appointment;
	
	@OneToOne
	@JoinColumn(name = "pat_id_fk_col")
	private Patient patient;
	
	@Column(name = "status_col")
	private String status;
	

}
