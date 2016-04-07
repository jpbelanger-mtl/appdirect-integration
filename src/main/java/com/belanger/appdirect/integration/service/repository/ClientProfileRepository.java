package com.belanger.appdirect.integration.service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import com.belanger.appdirect.integration.domain.data.ClientProfile;

public interface ClientProfileRepository extends Repository<ClientProfile, String> {
	Page<ClientProfile> findAll(Pageable pageable);

	ClientProfile findById(String id);

	ClientProfile findByCompanyUUID(String uuid);

	ClientProfile save(ClientProfile clientProfile);
}
