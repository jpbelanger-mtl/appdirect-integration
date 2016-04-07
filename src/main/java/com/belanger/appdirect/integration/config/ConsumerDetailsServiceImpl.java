package com.belanger.appdirect.integration.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth.common.OAuthException;
import org.springframework.security.oauth.common.signature.SharedConsumerSecretImpl;
import org.springframework.security.oauth.provider.BaseConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetailsService;

public class ConsumerDetailsServiceImpl implements ConsumerDetailsService {

	final static Logger log = LoggerFactory.getLogger(ConsumerDetailsServiceImpl.class);
	
	@Value("${oauth.key}")
	String oauthKey;

	@Value("${oauth.secret}")
	String oauthSecret;
	
	@Value("${oauth.name}")
	String oauthName;
	
	@Override
	public ConsumerDetails loadConsumerByConsumerKey(String consumerKey) throws OAuthException {

	    if (oauthKey.equals(consumerKey)) {
	        // allow this oauth request
	    	BaseConsumerDetails cd = new BaseConsumerDetails();
	        cd.setConsumerKey(consumerKey);
	        cd.setSignatureSecret(new SharedConsumerSecretImpl(oauthSecret));
	        cd.setConsumerName("AppDirect");
	        cd.setRequiredToObtainAuthenticatedToken(false); // no token required (0-legged)
	        cd.getAuthorities().add(new SimpleGrantedAuthority("ROLE_OAUTH")); // add the ROLE_OAUTH (can add others as well)
	        log.info("OAuth check SUCCESS, consumer key: " + consumerKey);

	        return cd;
	    } else {
	    	log.info("OAuth check FAILED with key " + consumerKey);
	        // deny - failed to match
	        throw new OAuthException("Provided key was invalid " + consumerKey);
	    }
	}
}
