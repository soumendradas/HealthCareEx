package in.nareshit.raghu.controller;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.nareshit.raghu.constants.SlotsStatus;
import in.nareshit.raghu.constants.UserRoles;
import in.nareshit.raghu.entity.Appointment;
import in.nareshit.raghu.entity.Patient;
import in.nareshit.raghu.entity.SlotRequest;
import in.nareshit.raghu.service.IAppointmentService;
import in.nareshit.raghu.service.IPatientService;
import in.nareshit.raghu.service.ISlotRequestService;

@Controller
@RequestMapping("/slots")
public class SlotRequestController {
	
	@Autowired
	private ISlotRequestService service;
	
	@Autowired
	private IAppointmentService appService;
	
	@Autowired
	private IPatientService patService;
	
	@GetMapping("book")
	public String bookSlot(@RequestParam Long id,
			Principal principal,
			Model model) {
		
		Appointment appointment = appService.getOneAppointment(id);
		Patient patient = patService.getOnePatientByEmail(principal.getName());
		
		//create slot object
		SlotRequest sr = new SlotRequest();
		sr.setAppointment(appointment);
		sr.setPatient(patient);
		sr.setStatus(SlotsStatus.PENDING.name());
		
		String message = "";
		try {
			service.saveSlotRequest(sr);
			
			String date = appointment.getDate()
						.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			
			message = "Patient "+patient.getFirstName()+" "+ patient.getLastName()+
					", Request for Dr."+appointment.getDoctor().getFirstName()+' '+
					appointment.getDoctor().getLastName()+", on Date: "+ date+
					", submitted with status: "+sr.getStatus();
			
		}catch (Exception e) {
			e.printStackTrace();
			message = "BOOKING REQUEST ALREADY MADE FOR THIS APPOINTMENT/DATE";
		}
		model.addAttribute("message", message);
		return "SlotRequestMessage";
		
	}
	
	@GetMapping("all")
	public String viewAllReq(Model model) {
		
		List<SlotRequest> list = service.getAllSlotrequest();
		
		model.addAttribute("list", list);
		return "SlotRequestData";
	}
	
	@GetMapping("/accept")
	public String updateSlotAccept(@RequestParam Long id) {
		service.updateSlotRequestStatus(id, SlotsStatus.ACCEPTED.name());
		appService.updateAppointmentSlot(service.getAppointmentId(id),SlotsStatus.ACCEPTED.name());
		return "redirect:all";
	}
	
	@GetMapping("/reject")
	public String updateSlotReject(@RequestParam Long id) {
		service.updateSlotRequestStatus(id, SlotsStatus.REJECTED.name());
		return "redirect:all";
	}

}
