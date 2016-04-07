package com.belanger.appdirect.integration.domain.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserFixtures {

	public List<User> getList()
	{
		List<User> list = new ArrayList<>();
		
		list.add(createBase());
		list.add(createBase2());

		return list;
	}

	public User createBase()
	{
		User user = new User();
		user.setId("123");
		user.setLastName("Doe");
		user.setFirstName("John");
		
		return user;
	}

	public User createBase2()
	{
		User user = new User();
		user.setId("456");
		user.setLastName("Doe");
		user.setFirstName("Jane");
		
		return user;
	}
}
