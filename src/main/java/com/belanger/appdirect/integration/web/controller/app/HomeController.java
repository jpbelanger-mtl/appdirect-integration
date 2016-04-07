package com.belanger.appdirect.integration.web.controller.app;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app")
public class HomeController {

	@RequestMapping("/home")
	@PreAuthorize("isAuthenticated()")
	public String home()
	{
		return "redirect:/index.html";
	}
	
}
