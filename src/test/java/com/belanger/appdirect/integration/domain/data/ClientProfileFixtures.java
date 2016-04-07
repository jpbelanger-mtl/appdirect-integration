package com.belanger.appdirect.integration.domain.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.belanger.appdirect.integration.domain.vo.AppDirectAccountStatus;

@Component
public class ClientProfileFixtures {

	public List<ClientProfile> getList()
	{
		List<ClientProfile> list = new ArrayList<>();
		
		list.add(createBase());
		list.add(createBase2());

		return list;
	}

	public ClientProfile createBase()
	{
		ClientProfile profile = new ClientProfile();
		profile.setId("12345");
		profile.setStatus(AppDirectAccountStatus.ACTIVE);
		
		return profile;
	}

	public ClientProfile createBase2()
	{
		ClientProfile profile = new ClientProfile();
		profile.setId("12346");
		profile.setStatus(AppDirectAccountStatus.ACTIVE);
		
		return profile;
	}
}
