package in.nareshit.raghu.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.nareshit.raghu.entity.Appointment;
import in.nareshit.raghu.entity.Doctor;
import in.nareshit.raghu.service.IAppointmentService;
import in.nareshit.raghu.service.IDoctorService;
import in.nareshit.raghu.service.ISpecializationService;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {

	@Autowired
	private IAppointmentService service;

	@Autowired
	private IDoctorService docService;
	
	@Autowired
	private ISpecializationService specService;
	
	private void getAllDoctorNamesAndId(Model model) {
		Map<Long, String> docs = docService.getDoctorIdNamesAndSpec();
		model.addAttribute("doctors", docs);
	}

	@GetMapping("/register")
	public String showRegisterPage(Model model,
			@RequestParam(value = "message", required = false) String message) {

		getAllDoctorNamesAndId(model);

		model.addAttribute("message", message);

		return "AppointmentRegister";
	}

	@PostMapping("save")
	public String save(@ModelAttribute Appointment appointment, 
			RedirectAttributes attributes) {
		String message = "";
		try {
			Long id = service.saveAppointment(appointment);
			message = "Appointment '" + id + "' is created";
		}catch (Exception e) {
			e.printStackTrace();
			message = "Appointment already created";
		}

		attributes.addAttribute("message", message);
		return "redirect:register";
	}

	@GetMapping("all")
	public String showAll(Model model, 
			@RequestParam(value = "message", required = false) String message,
			@RequestParam(value = "date", required = false) LocalDate date) {
		
		if(date == null) {
			List<Appointment> appointments = service.getAllAppointment();
			model.addAttribute("appointments", appointments);
			model.addAttribute("message", message);
			
		}else {
			
		}
		
		return "AppointmentData";
		
		
	}

	@GetMapping("delete")
	public String delete(@RequestParam Long id, RedirectAttributes attributes) {

		String message = null;
		try {
			service.removeAppointment(id);
			message = "Appointment "+id+" is deleted....";
		} catch (Exception e) {
			
			message = e.getMessage();
		}
		attributes.addAttribute("message", message);

		return "redirect:all";
	}
	
	@GetMapping("edit")
	public String showEditPage(@RequestParam Long id, Model model,
			RedirectAttributes attributes) {
		
		String page = null;
		
		try {
			Appointment appointment = service.getOneAppointment(id);
			model.addAttribute("appointment", appointment);
			getAllDoctorNamesAndId(model);
			page = "AppointmentEdit.html";
			
		}catch (Exception e) {
			attributes.addAttribute("message", e.getMessage());
			page = "redirect:all";
		}
		
		return page;
	}
	
	@PostMapping("update")
	public String update(@ModelAttribute Appointment appointment,
			RedirectAttributes attributes) {
		
		String message = null;
		try {
			service.updateAppointment(appointment);
			message = "Appointment "+ appointment.getId()+ " is updated";
			
		} catch (Exception e) {
			message = e.getMessage();
		}
		attributes.addAttribute("message", message);
		
		return "redirect:all";
	}
	
	// show doctors to book slot
	@GetMapping("/view")
	public String viewSlots(@RequestParam(required = false, 
			defaultValue = "0") Long specId, Model model) {
		
		Map<Long, String> specializations = specService.getSpecIdAndName(); 
		
		List<Doctor> docList = null;
		
		String message = null;
		
		if(specId == 0) {
			docList = docService.getAllDoctors();
			message = "Result : All Doctors"; 
		}else {
			docList = docService.findDoctorBySpecId(specId);
			message = "Result : "
					+specService.getOneSpecialization(specId).getSpecName()+" Doctors";
		}
		
		model.addAttribute("specializations", specializations);
		model.addAttribute("docList",docList);
		model.addAttribute("message", message);
		
		return "AppointmentSearch";
	}
	
	//view slots to book
	@GetMapping("viewSlot")
	public String showSlots(@RequestParam Long id, Model model) {
		
		LocalDate date = LocalDate.now();
		List<Object[]> appointments = service.getAppointmentByDoctor(id, date);
		Doctor doc = docService.getOneDoctor(id);
		
		model.addAttribute("list", appointments);
		model.addAttribute("message","Result for: "+
				doc.getFirstName()+" "+doc.getLastName());
		
		return "AppointmentSlots";
	}
	
	@GetMapping("/currentDoc")
	public String getCurrentDoctorAppointments(Model model, Principal principal) {
		
		List<Object[]> appointments = service
				.getAppointmentByDoctorEmail(principal.getName());
		
		model.addAttribute("appointments", appointments);
		
		return "AppointmentForDoctor";
	}
	
}
