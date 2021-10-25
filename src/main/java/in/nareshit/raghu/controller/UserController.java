package in.nareshit.raghu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.nareshit.raghu.service.IUserService;

@Controller
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private IUserService service;
	
	@GetMapping("changepassword")
	public String showChangePasswordPage(@RequestParam(required = false) String message,
			Model model) {
		
		model.addAttribute("message", message);
		return "UserPasswordChange";
	}
	
	@PostMapping("/updatePassword")
	public String updatePassword(@RequestParam String currentPassword, 
			@RequestParam String newPassword, RedirectAttributes attributes) {
		
		service.updatePassword(currentPassword, newPassword);
		
		attributes.addAttribute("message", "Your password is changed");
		
		return "redirect:changepassword";
	}

}
