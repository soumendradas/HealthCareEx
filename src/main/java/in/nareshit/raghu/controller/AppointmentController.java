package in.nareshit.raghu.controller;

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
import in.nareshit.raghu.service.IAppointmentService;
import in.nareshit.raghu.service.IDoctorService;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {

	@Autowired
	private IAppointmentService service;

	@Autowired
	private IDoctorService docService;
	
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

		Long id = service.saveAppointment(appointment);

		attributes.addAttribute("message", "Appointment '" + id + "' is created");
		return "redirect:register";
	}

	@GetMapping("all")
	public String showAll(Model model, 
			@RequestParam(value = "message", required = false) String message) {

		List<Appointment> appointments = service.getAllAppointment();
		model.addAttribute("appointments", appointments);
		model.addAttribute("message", message);
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

}
