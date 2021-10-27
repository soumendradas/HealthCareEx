package in.nareshit.raghu.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.nareshit.raghu.entity.User;
import in.nareshit.raghu.service.IUserService;

@Controller
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private IUserService service;
	
	@GetMapping("/login")
	public String showLoginPage() {
		
		return "UserLogin";
	}
	
	@GetMapping("/setup")
	public String setup(HttpSession session, Principal principal) {
		
		String username = principal.getName();
		User user = service.findByUsername(username).get();
		
		session.setAttribute("userOb", user);
		
		return "UserHome";
	}
	
	@GetMapping("changepassword")
	public String showChangePasswordPage(@RequestParam(required = false) String message,
			Model model) {
		
		model.addAttribute("message", message);
		return "UserPasswordChange";
	}
	
	@PostMapping("/updatePassword")
	public String updatePassword(@RequestParam String currentPassword, 
			@RequestParam String newPassword, RedirectAttributes attributes) {
		
		String message = service.updatePassword(currentPassword, newPassword);
		
		attributes.addAttribute("message", message);
		
		return "redirect:changepassword";
	}

}
