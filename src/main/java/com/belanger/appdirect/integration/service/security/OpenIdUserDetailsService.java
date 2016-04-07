package com.belanger.appdirect.integration.service.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Service;

import com.belanger.appdirect.integration.domain.data.User;
import com.belanger.appdirect.integration.service.repository.UserRepository;

@Service
public class OpenIdUserDetailsService implements AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

	private Logger logger = Logger.getLogger(OpenIdUserDetailsService.class);

	@Autowired
	private UserRepository userRepository;
	
	public UserDetails loadUserDetails(OpenIDAuthenticationToken token) throws UsernameNotFoundException {

		logger.info("Looking up openId: " + token.getIdentityUrl());
		final User user = userRepository.findByOpenId(token.getIdentityUrl());

		if ( user == null ) {
			throw new UsernameNotFoundException(token.getIdentityUrl());
		}
		else if (!user.isActive()) {
			throw new DisabledException("User is disabled");
		}
		
        return new UserDetails() {
			private static final long serialVersionUID = -8401211954671381589L;

			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();

				if ( user.isAdmin() )
					list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
				else
					list.add(new SimpleGrantedAuthority("ROLE_USER"));

				return list;
			}

			@Override
			public String getPassword() {
				return null;
			}

			@Override
			public String getUsername() {
				return user.getFirstName() + " " + user.getLastName();
			}

			@Override
			public boolean isAccountNonExpired() {
				return user.isActive();
			}

			@Override
			public boolean isAccountNonLocked() {
				return user.isActive();
			}

			@Override
			public boolean isCredentialsNonExpired() {
				return user.isActive();
			}

			@Override
			public boolean isEnabled() {
				return user.isActive();
			}
        };
    }
}