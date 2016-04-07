package com.belanger.appdirect.integration.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.belanger.appdirect.integration.domain.vo.AppDirectEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

@Component
public class EventFetchHelper {
	
	final static Logger log = LoggerFactory.getLogger(EventFetchHelper.class);

	@Autowired
	RestTemplate restTemplate;
	
	@Value("${oauth.key}")
	String oauthKey;

	@Value("${oauth.secret}")
	String oauthSecret;
	
	private ObjectMapper mapper = new ObjectMapper();

	public AppDirectEvent fetchEvent(String url)
	{
		try
		{
			OAuthConsumer consumer = new DefaultOAuthConsumer(oauthKey, oauthSecret);
			HttpURLConnection request = (HttpURLConnection) new URL(url).openConnection();
			request.setRequestProperty("Accept", "application/json");
			request.setRequestProperty("Content-Type", "application/json");
			consumer.sign(request);
			InputStream content = (InputStream) request.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(content));
		    String line;
		    StringBuilder builder = new StringBuilder();
		    while ((line = in.readLine()) != null) {
		        builder.append(line);
		    }

		    log.debug(builder.toString());

		    AppDirectEvent data = mapper.readValue(builder.toString(), AppDirectEvent.class);

		    return data;
		}
		catch ( IOException ex )
		{
			throw new RuntimeException(ex);
		} catch (OAuthMessageSignerException ex) {
			throw new RuntimeException(ex);
		} catch (OAuthExpectationFailedException ex) {
			throw new RuntimeException(ex);
		} catch (OAuthCommunicationException ex) {
			throw new RuntimeException(ex);
		}
	}
}
