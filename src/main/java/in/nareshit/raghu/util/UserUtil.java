package in.nareshit.raghu.util;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import in.nareshit.raghu.entity.User;

@Component
public class UserUtil {
	
	public String getPwd() {
		return UUID.randomUUID()
				.toString()
				.replace("-", "")
				.substring(0, 8);
	}
	

}
