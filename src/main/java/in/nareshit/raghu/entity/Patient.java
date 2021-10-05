package in.nareshit.raghu.entity;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

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
	private Long id;
	
	@Column(name = "pat_fn_col", nullable = false)
	private String firstName;
	
	@Column(name = "pat_ln_col", nullable = false)
	private String lastName;
	
	@Column(name = "pat_gen_col", nullable = false)
	private String gender;
	
	@Column(name = "pat_mob_col", nullable = false)
	private String mobile;
	
	@Column(name = "pat_dob_col")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dateOfBirth;
	
	@Column(name = "pat_ms_col", nullable = false)
	private String martialStatus;
	
	@Column(name = "pat_paddr_col", nullable = false)
	private String presentAddress;
	
	@Column(name = "pat_caddr_col", nullable = false)
	private String communicationAddress;
	
	@ElementCollection
	@CollectionTable(
			name = "pat_medi_hist_tab",
			joinColumns = @JoinColumn(name="pat_medi_hst_id_fk_col"))
	@Column(name = "pat_medi_hist_col")
	private Set<String> mediHistory;
	
	@Column(name = "pat_medi_hist_other")
	private String ifOther;
	
	@Column(name = "pat_note_col")
	private String note;
	

}
