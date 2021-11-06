package in.nareshit.raghu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.nareshit.raghu.entity.Specialization;
import in.nareshit.raghu.exception.SpecializationNotFoundException;
import in.nareshit.raghu.service.ISpecializationService;
import in.nareshit.raghu.view.SpecializationExcelView;

@Controller
@RequestMapping("/spec")
public class SpecializationController {

	@Autowired
	private ISpecializationService service;

	/**
	 * 1. show register page
	 */
	@GetMapping("/register")
	public String showRegister(@RequestParam(value = "message", required = false) String message, Model model) {
		model.addAttribute("message", message);
		return "SpecializationRegister";
	}
	
	/**
	 * 2. save specialization data
	 * @param specialization
	 * @param attributes
	 * @return
	 */

	@PostMapping("/save")
	public String saveForm(@ModelAttribute Specialization specialization, RedirectAttributes attributes) {

		Long id = service.saveSpecialization(specialization);
		String message = "Record (" + id + ") is created";
		attributes.addAttribute("message", message);

		return "redirect:register";
	}
	
	/**
	 * 3. view all specialization data
	 * @param model
	 * @param message
	 * @return
	 */

//	@GetMapping("/all")
//	public String viewAll(Model model, @RequestParam(value = "message", required = false) String message) {
//		List<Specialization> allSpec = service.getAllSpecializations();
//		model.addAttribute("allSpec", allSpec);
//		model.addAttribute("message", message);
//		return "SpecializationData.html";
//	}
	
	/*
	 * pageable view
	 */
	@GetMapping("/all")
	public String viewAllPageble(@PageableDefault(page = 0, size = 3) Pageable pageable,
			Model model,
			@RequestParam(value = "message", required = false) String message,
			@RequestParam(value = "sizeAtRuntime", required = false, defaultValue = "0") Integer sizeAtRuntime) {
		
		pageable = PageRequest.of(pageable.getPageNumber(), 
				sizeAtRuntime !=0?sizeAtRuntime:pageable.getPageSize());
		Page<Specialization> page = service.getAllSpecializtions(pageable);
		model.addAttribute("allSpec", page.getContent());
		model.addAttribute("page", page);
		model.addAttribute("message",message);
		
		return "SpecializationData";
	}
	
	/**
	 * 4. Delete one specialization data
	 * @param id
	 * @param attributes
	 * @return
	 */

	@GetMapping("/delete")
	public String deleteData(@RequestParam Long id, RedirectAttributes attributes) {
		try {
			service.removeSpecialization(id);

			String message = "Record (" + id + ") deleted..";
			attributes.addAttribute("message", message);
		} catch (SpecializationNotFoundException e) {
			attributes.addAttribute("message", e.getMessage());
		}

		return "redirect:all";
	}
	
	/**
	 * 5. show edit page
	 * @param id
	 * @param model
	 * @param attributes
	 * @return
	 */

	@GetMapping("/edit")
	public String showEditPage(@RequestParam Long id,
			Model model,
			RedirectAttributes attributes) {

		String page = "";
		try {
			Specialization specialization = service.getOneSpecialization(id);
			model.addAttribute("specialization", specialization);
			page = "SpecializationEdit";

		} catch (Exception e) {
			attributes.addAttribute("message", e.getMessage());
			page = "redirect:all";
		}

		return page;
	}
	
	/**
	 * 6. update specialization data
	 * @param specialization
	 * @param attributes
	 * @return
	 */

	@PostMapping("/update")
	public String updateDate(@ModelAttribute Specialization specialization, RedirectAttributes attributes) {
		
		try {
		String message = service.updateSpecialization(specialization);
		attributes.addAttribute("message", message);
		}catch (Exception e) {
			attributes.addAttribute("message", e.getMessage());
		}

		return "redirect:all";
	}
	
	/**
	 * 7. check code if exist
	 * @param code
	 * @return
	 */

	@GetMapping("/checkCode")
	@ResponseBody
	public String validateSpecCode(@RequestParam String code, 
			@RequestParam Long id) {

		String message = "";

		if (service.isSpecCodeExist(code, id)) {
			message = code + ", already exist";
		}

		return message;
	}
	
	/**
	 * 8. check name if exist
	 * @param name
	 * @return
	 */

	@GetMapping("/checkName")
	@ResponseBody
	public String validateSpecName(@RequestParam String name,
			@RequestParam Long id) {

		String message = "";
		if (service.isSpecNameExist(name, id)) {
			message = name + ", already exist";
		}

		return message;
	}
	
	/**
	 * 9. Excel Export
	 * @return
	 */
	@GetMapping("/excel")
	public ModelAndView exportToExcel() {
		
		ModelAndView mv = new ModelAndView();
		mv.setView(new SpecializationExcelView());
		List<Specialization> list = service.getAllSpecializations();
		mv.addObject("list", list);
		
		return mv;
	}

}
