package com.belanger.appdirect.integration.utils;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.CommonsRequestLoggingFilter;

public class SimpleRequestLoggerFilter extends CommonsRequestLoggingFilter {

	@Override
	protected String createMessage(HttpServletRequest request, String prefix, String suffix) {
		String str = super.createMessage(request, prefix, suffix);
		StringBuilder builder = new StringBuilder(str);
		
		Enumeration<String> headerNames = request.getHeaderNames();
		while ( headerNames.hasMoreElements() ) {
			String name = headerNames.nextElement();
			builder.append("[" + name + "=" + request.getHeader(name) + "]");
		}

		return builder.toString();
	}
}
