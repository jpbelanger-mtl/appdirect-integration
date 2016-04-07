package com.belanger.appdirect.integration.web.controller.app;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.belanger.appdirect.integration.domain.data.User;
import com.belanger.appdirect.integration.service.repository.UserRepository;

@RestController
@RequestMapping("/app")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/users")
	@PreAuthorize("isAuthenticated()")
	public List<User> users()
	{
		Page<User> users = userRepository.findAll(new PageRequest(0, 100));

		return users.getContent();
	}

	@RequestMapping("/info")
	@PreAuthorize("isAuthenticated()")
	public Principal info(Principal principal, HttpServletResponse response)
	{
		if ( principal == null )
			response.setStatus(HttpStatus.SC_NO_CONTENT);

		return principal;
	}

}
