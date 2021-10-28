package in.nareshit.raghu.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.nareshit.raghu.constants.UserRoles;
import in.nareshit.raghu.entity.Patient;
import in.nareshit.raghu.entity.User;
import in.nareshit.raghu.service.IPatientService;
import in.nareshit.raghu.util.UserUtil;

@Controller
@RequestMapping("patient")
public class PatientController {
	
	@Autowired
	private IPatientService service;
	
	@Autowired
	private UserUtil userUtil;
	
	@GetMapping("/register")
	public String showRegisterPage(
			@RequestParam(name = "message", required = false) String message,
			Model model) {
		
		model.addAttribute("message", message);
		
		return "PatientRegister";
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute Patient patient,
			RedirectAttributes attributes) {
		
		Long id = service.savePatient(patient);
		attributes.addAttribute("message", "Patient "+id+" created");
		return "redirect:register";
	}
	
	@GetMapping("/all")
	public String showAll(Model model,
			@RequestParam(value = "message", required = false) String message) {
		
		List<Patient> patients = service.getAllPatients();
		model.addAttribute("patients", patients);
		model.addAttribute("message", message);
		
		return "PatientData";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam Long id,
			RedirectAttributes attributes) {
		String message = "";
		try {
			service.removePatient(id);
			message = "Patient "+id+" deleted";
			
		}catch (Exception e) {
			message = e.getMessage();
		}
		attributes.addAttribute("message", message);
		
		return "redirect:all";
	}
	
	@GetMapping("/edit")
	public String showEditPage(@RequestParam(value = "id", required = false) Long id,
			RedirectAttributes attributes,
			Model model) {
		String page = "";
		User user = userUtil.getUser();
		try {
			Patient patient = null;
			if(user.getRole().equals(UserRoles.ADMIN.name())) {
				patient = service.getOnePatient(id);
			}else {
				patient = service.getOnePatientByEmail(user.getUsername());
			}
			model.addAttribute("patient", patient);
			page = "PatientEdit";
		}catch (Exception e) {
			attributes.addAttribute("message", e.getMessage());
			page = "redirect:all";
		}
		
		return page;
	}
	
	@PostMapping("update")
	public String update(@ModelAttribute Patient patient,
			RedirectAttributes attributes) {
		String message = "";
		User user = userUtil.getUser();
		try {
			service.updatePatient(patient);
			message = "Patient "+patient.getId()+ " updated";
		}catch (Exception e) {
			message = e.getMessage();
		}
		attributes.addAttribute("message", message);
		
		if(user.getRole().equals(UserRoles.ADMIN.name())) {
			return "redirect:all";
		}
		return "redirect:showProfile";
	}
	
	@GetMapping("checkEmail")
	@ResponseBody
	public String validateEmail(@RequestParam(value = "id") Long id,
			@RequestParam(value = "email") String email) {
		String message = "";
		if(service.isEmailExist(id, email)) {
			message = "<b>Email</b> is exist";
		}
		
		return message;
	}
	
	@RequestMapping("showProfile")
	public String viewProfile(@RequestParam(value = "message", required = false) String message
			, Model model) {
		
		User user = userUtil.getUser();
		
		Patient patient = service.getOnePatientByEmail(user.getUsername());
		model.addAttribute("pat", patient);
		model.addAttribute("message", message);
		
		return "PatientProfile";
	}

}
