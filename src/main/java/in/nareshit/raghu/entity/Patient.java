package in.nareshit.raghu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "patient_tab")
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	@Column(name = "pat_fn_col")
	public String firstName;
	
	@Column(name = "pat_ln_col")
	public String lastName;
	
	@Column(name = "pat_gen_col")
	public String gender;
	
	@Column(name = "pat_mob_col")
	public String mobile;
	
	@Column(name = "pat_dob_col")
	public String dob;
	
	@Column(name = "pat_ms_col")
	public String martialStatus;
	
	@Column(name = "pat_pres_addr_col")
	public String presentAddress;
	
	@Column(name = "pat_comm_addr_col")
	public String communicationAddress;
	
	@Column(name = "pat_oth_col")
	public String otherDetails;

}
