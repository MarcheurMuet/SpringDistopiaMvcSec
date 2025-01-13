package fr.distopia.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	/**
	 * @return login.html
	 */
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	/**
	 * @param request
	 * @param response
	 * @return login.html
	 */
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login";
	}
	
	/**
	 * @return redirection vers index.html
	 */
	@GetMapping("/")
	public String home() {
		return "redirect:/index";
	}
	
	/**
	 * @return 403.html
	 */
	@GetMapping("/403")
	public String accessDenied() {		
		return "error/403";
	}
	
	/**
	 * @return 404.html
	 */
	@GetMapping("/error")
	public String error() {
		return "error/404";
	}	
}
