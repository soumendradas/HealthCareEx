package in.nareshit.raghu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import in.nareshit.raghu.service.IUserService;

@Controller
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private IUserService service;
	
	@GetMapping("changepassword")
	public String showChangePasswordPage() {
		
		return "UserPasswordChange";
	}
	
	@PostMapping("/updatePassword")
	public String updatePassword(@ModelAttribute Object pass) {
		
		System.out.println();
		
		return "UserPasswordChange";
	}

}
