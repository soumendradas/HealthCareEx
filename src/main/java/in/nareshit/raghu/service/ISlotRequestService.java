package in.nareshit.raghu.service;

import java.util.List;

import in.nareshit.raghu.entity.SlotRequest;

public interface ISlotRequestService {
	//patient can book slot
	Long saveSlotRequest(SlotRequest slotRequest);
	//Admin can view all request
	List<SlotRequest> getAllSlotrequest();
	
	// Admin/patient can update status
	void updateSlotRequestStatus(Long id, String status);
	
	//Patient can see his slots
	List<SlotRequest> viewSlotsByPatient(String patientUsername);
}
