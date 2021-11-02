package in.nareshit.raghu.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.nareshit.raghu.entity.SlotRequest;
import in.nareshit.raghu.repository.SlotRequestRepository;
import in.nareshit.raghu.service.ISlotRequestService;

@Service
public class SlotRequestImpl implements ISlotRequestService {
	
	@Autowired
	private SlotRequestRepository repo;

	@Override
	public Long saveSlotRequest(SlotRequest slotRequest) {
		
		return repo.save(slotRequest).getId();
	}

	@Override
	public List<SlotRequest> getAllSlotrequest() {
		
		return repo.findAll();
	}

	@Transactional
	@Override
	public void updateSlotRequestStatus(Long id, String status) {
		
		repo.updateSlotRequestStatus(id, status);
	}

	@Override
	public List<SlotRequest> viewSlotsByPatient(String patientUsername) {
		
		return repo.getAllPatientSlots(patientUsername);
	}
	
	
	@Override
	public SlotRequest getOneSlotRequest(Long id) {
		
		return repo.findById(id).get();
	}
	
	@Override
	public List<SlotRequest> viewSlotsByDoctor(String doctorUsername, String status) {
		
		return repo.getAllDoctorSlots(doctorUsername, status);
	}

	
}
