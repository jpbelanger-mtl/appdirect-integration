package com.belanger.appdirect.integration.web.controller.login;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

	final static Logger log = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping("/login")
	public String login(Map<String, Object> model)
	{
		log.info("Forwarding to login form View");
		return "loginForm";
	}

	@RequestMapping("/denied")
	public String denied(Map<String, Object> model)
	{
		log.info("Forwarding to login denied");
		return "deniedForm";
	}
}
