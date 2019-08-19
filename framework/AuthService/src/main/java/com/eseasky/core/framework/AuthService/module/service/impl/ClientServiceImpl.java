package com.eseasky.core.framework.AuthService.module.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import com.eseasky.core.framework.AuthService.module.repository.ClientRepository;
import com.eseasky.core.starters.auth.server.core.interfaces.ClientService;

@Service
public class ClientServiceImpl implements ClientService {
	@Autowired
	ClientRepository clientRepository;
	@Override
	public ClientDetails get(String clientId) {
		// TODO Auto-generated method stub
		return clientRepository.findByClientId(clientId);
	}

	@Override
	public void check(ClientDetails details) throws ClientRegistrationException {
		// TODO Auto-generated method stub
		return;
	}

}
