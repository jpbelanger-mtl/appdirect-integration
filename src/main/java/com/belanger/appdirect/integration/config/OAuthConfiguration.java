package com.belanger.appdirect.integration.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.oauth.provider.ConsumerDetailsService;
import org.springframework.security.oauth.provider.filter.ProtectedResourceProcessingFilter;
import org.springframework.security.oauth.provider.token.InMemorySelfCleaningProviderTokenServices;
import org.springframework.security.oauth.provider.token.OAuthProviderTokenServices;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.security.openid.OpenIDConsumer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuthConfiguration {
	@Bean
	public ConsumerDetailsService consumerDetailsService() {
		return new ConsumerDetailsServiceImpl();
	}

	@Bean
	public OAuthProviderTokenServices oauthProviderTokenServices() {
		return new InMemorySelfCleaningProviderTokenServices();
	}

	@Bean
	public ProtectedResourceProcessingFilter protectedResourceProcessingFilter() {
		ProtectedResourceProcessingFilter prpf = new ProtectedResourceProcessingFilter();
		prpf.setConsumerDetailsService(consumerDetailsService());
		prpf.setTokenServices(oauthProviderTokenServices());
		return prpf;
	}
	
	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return new Http403ForbiddenEntryPoint();
	}
	
	@Order(50)
	@Configuration
	public static class ApiOAuthConfiguration extends WebSecurityConfigurerAdapter {
		@Autowired
		private AuthenticationEntryPoint authenticationEntryPoint;

		@Autowired
		private ProtectedResourceProcessingFilter prpf;

		@Override
		protected void configure(HttpSecurity httpSecurity) throws Exception {
			httpSecurity.antMatcher("/api/**")
						.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
						.csrf().disable()
						.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
					.and()
						.authorizeRequests().anyRequest().authenticated()
					.and()
						.addFilterBefore(prpf, UsernamePasswordAuthenticationFilter.class);
		}
	}

/*	@Order(20)
	@Configuration
	public static class WebAppOAuthConfiguration extends WebSecurityConfigurerAdapter {
		@Autowired
		private AuthenticationEntryPoint authenticationEntryPoint;

		@Autowired
		private ProtectedResourceProcessingFilter prpf;

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.antMatcher("/index.html")
				.csrf().disable()
				.authorizeRequests().anyRequest().authenticated()
					.and()
						.addFilterBefore(prpf, UsernamePasswordAuthenticationFilter.class);
		}
	}
*/
	@Order(80)
	@Configuration
	public static class LoginWebSecurityAdapter extends WebSecurityConfigurerAdapter {

		@Autowired
		private AuthenticationUserDetailsService<OpenIDAuthenticationToken> openIdUserDetailsService;

		@Autowired
		private OpenIDConsumer openIdConsumer;

		@Autowired
		private AuthenticationFailureHandler authenticationFailureHandler;

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
					.authorizeRequests()
						.anyRequest().authenticated()
					.and()
						.csrf().disable()
						.logout().logoutSuccessUrl("http://www.appdirect.com")
					.and()
						.openidLogin()
						.failureHandler(authenticationFailureHandler)
						.loginPage("/login")
						.permitAll()
						.loginProcessingUrl("/openid")
						.defaultSuccessUrl("/app/home")
						.authenticationUserDetailsService(openIdUserDetailsService)
						.consumer(openIdConsumer);
		}
	}
}
