package in.nareshit.raghu.controller;

import java.util.List;

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

import in.nareshit.raghu.entity.Specialization;
import in.nareshit.raghu.service.ISpecializationService;


@Controller
@RequestMapping("/spec")
public class SpecializationController {
	
	@Autowired
	private ISpecializationService service;
	
	/**
	 * 1. show register page
	 */
	@GetMapping("/register")
	public String showRegister(
			@RequestParam(value = "message", required = false) String message,
			Model model) {
		model.addAttribute("message", message);
		return "SpecializationRegister";
	}
	
	@PostMapping("/save")
	public String saveForm(@ModelAttribute Specialization specialization,
			RedirectAttributes attributes) {
		
		Long id = service.saveSpecialization(specialization);
		String message = "Record ("+id+") is created";
		attributes.addAttribute("message", message);
		
		return "redirect:register";
	}
	
	@GetMapping("/all")
	public String viewAll(Model model,
			@RequestParam(value = "message", required = false) String message) {
		List<Specialization> allSpec = service.getAllSpecializations();
		model.addAttribute("allSpec", allSpec);
		model.addAttribute("message", message);
		return "SpecializationData.html";
	}
	
	
	@GetMapping("/delete")
	public String deleteData(@RequestParam Long id,
			RedirectAttributes attributes) {
		
		service.removeSpecialization(id);
		
		String message = "Record ("+id+") deleted..";
		attributes.addAttribute("message", message);
		
		return "redirect:all";
	}
	
	@GetMapping("/edit")
	public String showEditPage(@RequestParam Long id, 
			Model model) {
		Specialization specialization = service.getOneSpecialization(id);
		model.addAttribute("specialization", specialization);
		
		return "SpecializationEdit.html";
	}
	
	@PostMapping("/update")
	public String updateDate(@ModelAttribute Specialization specialization,
			RedirectAttributes attributes) {
		
		String message = service.updateSpecialization(specialization);
		attributes.addAttribute("message", message);
		
		return "redirect:all";
	}
	
	@GetMapping("/checkCode")
	@ResponseBody
	public String validateSpecCode(@RequestParam String code) {
		
		String message = "";
		
		if(service.isSpecCodeExist(code)) {
			message = code + ", already exist";
		}
		
		return message;
	}
	
	@GetMapping("/checkName")
	@ResponseBody
	public String validateSpecName(@RequestParam String name) {
		
		String message = "";
		if(service.isSpecNameExist(name)) {
			message = name +", already exist";
		}
		
		return message;
	}
	
	
}
