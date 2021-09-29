package in.nareshit.raghu.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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

import in.nareshit.raghu.entity.Doctor;
import in.nareshit.raghu.exception.DoctorNotFoundException;
import in.nareshit.raghu.service.IDoctorService;
import in.nareshit.raghu.service.ISpecializationService;
import in.nareshit.raghu.util.MyMailUtil;
import in.nareshit.raghu.view.DoctorExcelView;

@Controller
@RequestMapping("doctor")
public class DoctorController {

	@Autowired
	private IDoctorService service;

	@Autowired
	private ISpecializationService specService;

	@Autowired
	private MyMailUtil mailUtil;

	private void createDynamicUi(Model model) {
		Map<Long, String> specializtions = specService.getSpecIdAndName();
		model.addAttribute("specializations", specializtions);
	}

	@GetMapping("register")
	public String showRegister(@RequestParam(value = "message", required = false) String message, Model model) {

		model.addAttribute("message", message);
		createDynamicUi(model);

		return "DoctorRegister";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute Doctor doctor, RedirectAttributes redirectAttributes) {
		Long id = service.saveDoctor(doctor);
		String message = "Doctor '" + id + "' created";
		redirectAttributes.addAttribute("message", message);

		if (id != null) {
			new Thread(() -> {
				mailUtil.send(doctor.getEmail(), "Doctor Id creation Success", message,
						new ClassPathResource("/static/myRes/demo.pdf"));

			}).start();
		}

		return "redirect:register";
	}

	@GetMapping("all")
	public String showAll(Model model, @RequestParam(value = "message", required = false) String message) {
		List<Doctor> list = service.getAllDoctors();
		model.addAttribute("list", list);
		model.addAttribute("message", message);

		return "DoctorData";
	}

	@GetMapping("delete")
	public String delete(@RequestParam Long id, RedirectAttributes attributes) {
		String message = "";
		try {
			service.removeDoctor(id);
			message = "Doctor " + id + " deleted";
		} catch (DoctorNotFoundException e) {
			message = e.getMessage();
		}
		attributes.addAttribute("message", message);
		return "redirect:all";
	}

	@GetMapping("edit")
	public String showEditPage(@RequestParam Long id, Model model, RedirectAttributes attributes) {
		String page = "";
		try {
			Doctor doctor = service.getOneDoctor(id);
			model.addAttribute("doctor", doctor);
			createDynamicUi(model);
			page = "DoctorEdit";

		} catch (DoctorNotFoundException e) {
			attributes.addAttribute("message", e.getMessage());
			page = "redirect:all";
		}

		return page;
	}

	@PostMapping("/update")
	public String update(@ModelAttribute Doctor doctor, RedirectAttributes attributes) {

		String message = "";
		try {
			service.updateDoctor(doctor);
			message = "Doctor " + doctor.getId() + " updated";
		} catch (DoctorNotFoundException e) {
			message = e.getMessage();
		}
		attributes.addAttribute("message", message);
		return "redirect:all";
	}

	// -------------------------------------------------------------------------------
	@GetMapping("checkEmail")
	@ResponseBody
	public String validateEmail(@RequestParam("email") String email, @RequestParam("id") Long id) {
		String message = "";
		if (service.isEmailExist(email, id)) {
			message = "<b>" + email + "</b> is exist";
		}
		return message;
	}

	@GetMapping("checkMobile")
	@ResponseBody
	public String validateMobile(@RequestParam("mobile") String mobile, @RequestParam("id") Long id) {
		String message = "";
		if (service.isMobileExist(mobile, id)) {
			message = "<b>" + mobile + "</b> is exist";
		}
		return message;
	}

	@GetMapping("/excel")
	public ModelAndView exportToExcel() {
		ModelAndView mv = new ModelAndView();
		mv.setView(new DoctorExcelView());
		List<Doctor> doctors = service.getAllDoctors();
		mv.addObject("doctors", doctors);

		return mv;
	}

}
