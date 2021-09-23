package in.nareshit.raghu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nareshit.raghu.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}
