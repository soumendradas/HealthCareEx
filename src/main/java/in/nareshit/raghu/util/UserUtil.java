package in.nareshit.raghu.util;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {
	
	public String getPwd() {
		return UUID.randomUUID()
				.toString()
				.replace("-", "")
				.substring(0, 8);
	}
	
	public String getLoginUsername() {
		
		return SecurityContextHolder.getContext()
				.getAuthentication().getName();
	}
	
	public List<String> getLoginUserRole() {
		 return SecurityContextHolder.getContext()
				.getAuthentication().getAuthorities()
				.stream().map(t->t.getAuthority().toString()).toList();
		 
	}

}
