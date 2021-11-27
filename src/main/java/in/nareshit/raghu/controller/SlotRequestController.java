package in.nareshit.raghu.controller;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import in.nareshit.raghu.constants.SlotStatus;
import in.nareshit.raghu.entity.Appointment;
import in.nareshit.raghu.entity.Patient;
import in.nareshit.raghu.entity.SlotRequest;
import in.nareshit.raghu.service.IAppointmentService;
import in.nareshit.raghu.service.IDoctorService;
import in.nareshit.raghu.service.IPatientService;
import in.nareshit.raghu.service.ISlotRequestService;
import in.nareshit.raghu.service.ISpecializationService;
import in.nareshit.raghu.util.AdminDashboardUtil;
import in.nareshit.raghu.view.InvoicePdfView;

@Controller
@RequestMapping("/slots")
public class SlotRequestController {
	
	@Autowired
	private ISlotRequestService service;
	
	@Autowired
	private IAppointmentService appService;
	
	@Autowired
	private IPatientService patService;
	
	@Autowired
	private IDoctorService doctService;
	
	@Autowired
	private ISpecializationService specService;
	
	@Autowired
	private AdminDashboardUtil adminUtil;
	
	@Autowired
	private ServletContext context;
	
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
		sr.setStatus(SlotStatus.PENDING.name());
		
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
		
		Appointment app = service.getOneSlotRequest(id).getAppointment();
		
		if(app.getNoOfSlots() > 0) {
		
			service.updateSlotRequestStatus(id, SlotStatus.APPROVED.name());
			appService.updateAppointmentSlot(service.getOneSlotRequest(id)
					.getAppointment().getId(),-1);
			
		}
		return "redirect:all";
	}
	
	@GetMapping("/reject")
	public String updateSlotReject(@RequestParam Long id) {
		service.updateSlotRequestStatus(id, SlotStatus.REJECTED.name());
		return "redirect:all";
	}
	
	@GetMapping("/patient")
	public String viewByPatient(Principal principal,
			Model model) {
		
		List<SlotRequest> list = service.viewSlotsByPatient(principal.getName());
		model.addAttribute("list", list);
		
		return "SlotRequestDataPatient";
	}
	
	@GetMapping("/cancel")
	public String cancelRequest(@RequestParam Long id) {
		
		SlotRequest sl = service.getOneSlotRequest(id);
		service.updateSlotRequestStatus(id, SlotStatus.CANCELLED.name());
		
		if(sl.getStatus().equals(SlotStatus.APPROVED.name())) {
			appService.updateAppointmentSlot(sl.getAppointment().getId(), 1);	
		}
		
		return "redirect:patient";
	}
	
	@GetMapping("/doctor")
	public String viewByDoctor(Principal principal, Model model) {
		
		List<SlotRequest> list =service.viewSlotsByDoctor(principal.getName(),
				SlotStatus.APPROVED.name());
		model.addAttribute("list", list);
		return "SlotRequestDataDoctor";
	}
	
	@GetMapping("/dashboard")
	public String viewDashboard(Model model) {
		model.addAttribute("doctors", doctService.getAllDoctorsCount());
		model.addAttribute("appointments", appService.getAllSpecializationCount());
		model.addAttribute("patients", patService.getAllPatientCount());
		model.addAttribute("specializations", specService.getAllSpecializationCount());
		
		String path = context.getRealPath("/");
		List<Object[]> list = service.getSlotStausAndCount();
		
		adminUtil.generateBar(path, list);
		adminUtil.generatePie(path, list);
		
		return "AdminDashboard";
	}
	
	@GetMapping("/invoice")
	public ModelAndView showInvoice(@RequestParam Long id,
			ModelAndView mv) {
		mv.setView(new InvoicePdfView());
		SlotRequest sr = service.getOneSlotRequest(id);
		mv.addObject("slots", sr);
		
		return mv;
		
	}
	

}
