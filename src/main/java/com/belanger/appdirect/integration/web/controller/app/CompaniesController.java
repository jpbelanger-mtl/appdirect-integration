package com.belanger.appdirect.integration.web.controller.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.belanger.appdirect.integration.domain.data.ClientProfile;
import com.belanger.appdirect.integration.service.repository.ClientProfileRepository;

@RestController
@RequestMapping("/app")
public class CompaniesController {

	@Autowired
	private ClientProfileRepository clientProfileRepository;

	@RequestMapping("/companies")
	@PreAuthorize("isAuthenticated()")
	public List<ClientProfile> users()
	{
		Page<ClientProfile> clientProfiles = clientProfileRepository.findAll(new PageRequest(0, 100));

		return clientProfiles.getContent();
	}
}
