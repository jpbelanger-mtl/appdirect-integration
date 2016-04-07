package com.belanger.appdirect.integration.service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import com.belanger.appdirect.integration.domain.data.User;

public interface UserRepository extends Repository<User, String> {
	Page<User> findAll(Pageable pageable);
	User findById(String id);
	User findByUuid(String uuid);
	User findByOpenId(String openIdUrl);
	User save(User clientProfile);
}
