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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doctor_tab")
public class Doctor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "doc_id_col")
	private Long id;
	
	@Column(name = "doc_fn_col", nullable = false, length = 15)
	private String firstName;
	
	@Column(name = "doc_ln_col", nullable = false, length = 15)
	private String lastName;
	
	@Column(name = "doc_email_col", unique = true, nullable = false, length = 45)
	private String email;
	
	@Column(name="doc_addr_col", nullable = false, length = 40)
	private String address;
	
	@Column(name = "doc_mob_col", unique = true, nullable = false, length = 10)
	private String mobile;
	
	@Column(name = "doc_gen_col", nullable = false, length = 10)
	private String gender;
	
	@Column(name = "doc_note_col", nullable = false, length = 250)
	private String note;
	
	

}
