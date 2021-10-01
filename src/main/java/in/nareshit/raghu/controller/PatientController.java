package in.nareshit.raghu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.nareshit.raghu.entity.Patient;
import in.nareshit.raghu.service.IPatientService;

@Controller
@RequestMapping("patient")
public class PatientController {
	
	@Autowired
	private IPatientService service;
	
	@GetMapping("/register")
	public String showRegisterPage(
			@RequestParam(name = "message", required = false) String message,
			Model model) {
		
		model.addAttribute("message", message);
		
		return "PatientRegister";
	}
	
	@PostMapping("save")
	public String save(@ModelAttribute Patient patient,
			RedirectAttributes attributes) {
		
		Long id = service.savePatient(patient);
		attributes.addAttribute("message", "Patient "+id+" created");
		return "redirect:register";
	}

}
