package com.belanger.appdirect.integration.web.controller.api;

import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.belanger.appdirect.integration.domain.vo.AppDirectEvent;
import com.belanger.appdirect.integration.domain.vo.AppDirectNotificationResponse;
import com.belanger.appdirect.integration.service.AppDirectSubscriptionService;
import com.belanger.appdirect.integration.utils.EventFetchHelper;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController
{
	final static Logger log = LoggerFactory.getLogger(SubscriptionController.class);

	@Autowired
	private AppDirectSubscriptionService subscriptionService;

	@Autowired
	private EventFetchHelper eventFetchHelper;
	
	@RequestMapping("/create")
	public @ResponseBody AppDirectNotificationResponse create(@RequestParam(value="eventUrl") String eventUrl)
	{
		AppDirectEvent event = eventFetchHelper.fetchEvent(eventUrl);
		log.debug(ToStringBuilder.reflectionToString(event, new RecursiveToStringStyle()));

		AppDirectNotificationResponse result = subscriptionService.create(event);

		log.debug(ToStringBuilder.reflectionToString(result, new RecursiveToStringStyle()));
		return result;
	}

	@RequestMapping("/update")
	public @ResponseBody AppDirectNotificationResponse update(@RequestParam(value="eventUrl") String eventUrl)
	{
		AppDirectEvent event = eventFetchHelper.fetchEvent(eventUrl);
		log.debug(ToStringBuilder.reflectionToString(event, new RecursiveToStringStyle()));

		AppDirectNotificationResponse result = subscriptionService.update(event);

		log.debug(ToStringBuilder.reflectionToString(result, new RecursiveToStringStyle()));
		return result;
	}

	@RequestMapping("/cancel")
	public @ResponseBody AppDirectNotificationResponse cancel(@RequestParam(value="eventUrl") String eventUrl)
	{
		AppDirectEvent event = eventFetchHelper.fetchEvent(eventUrl);
		log.debug(ToStringBuilder.reflectionToString(event, new RecursiveToStringStyle()));

		AppDirectNotificationResponse result = subscriptionService.cancel(event);

		log.debug(ToStringBuilder.reflectionToString(result, new RecursiveToStringStyle()));
		return result;
	}

	@RequestMapping("/status")
	public @ResponseBody AppDirectNotificationResponse status(@RequestParam(value="eventUrl") String eventUrl)
	{
		AppDirectEvent event = eventFetchHelper.fetchEvent(eventUrl);
		log.debug(ToStringBuilder.reflectionToString(event, new RecursiveToStringStyle()));

		AppDirectNotificationResponse result = subscriptionService.status(event);

		log.debug(ToStringBuilder.reflectionToString(result, new RecursiveToStringStyle()));
		return result;
	}
}
