package in.nareshit.raghu.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import in.nareshit.raghu.constants.UserRoles;
import in.nareshit.raghu.entity.User;
import in.nareshit.raghu.service.IUserService;
import in.nareshit.raghu.util.UserUtil;

@Component
public class MasterAccountSetupRunner implements CommandLineRunner {
	
	@Value("${master.user.name}")
	private String displayName;
	
	@Value("${master.user.email}")
	private String email;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private UserUtil userUtil;

	@Override
	public void run(String... args) throws Exception {
		if(!userService.findByUsername(email).isPresent()) {
			User user = new User();
			user.setDisplayName(displayName);
			user.setUsername(email);
			user.setPassword(userUtil.getPwd());
			user.setRole(UserRoles.ADMIN.name());
			userService.saveUser(user);
			//TODO: EMAIL SERVICE
		}
		
	}

}
