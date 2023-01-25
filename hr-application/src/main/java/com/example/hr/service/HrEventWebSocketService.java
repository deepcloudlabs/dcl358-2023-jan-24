package com.example.hr.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.example.hr.domain.event.HrDomainEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@ConditionalOnProperty(name="messaging", havingValue = "kafka-websocket")
public class HrEventWebSocketService {
	private final WebsocketTemplate websocketTemplate;
	private final ObjectMapper objectMapper;
	
	public HrEventWebSocketService(
			WebsocketTemplate websocketTemplate, ObjectMapper objectMapper) {
		this.websocketTemplate = websocketTemplate;
		this.objectMapper = objectMapper;
	}

	@EventListener
	public void publishEvent(HrDomainEvent event) {
		try {
			var eventAsJson = objectMapper.writeValueAsString(event);
			websocketTemplate.sendMessage(eventAsJson);
		} catch (JsonProcessingException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
