package com.example.hr.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.example.hr.domain.event.HrDomainEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@ConditionalOnProperty(name="messaging", havingValue = "kafka-websocket")
public class HrEventRabbitService {
	private final RabbitTemplate rabbitTemplate;
	private final ObjectMapper objectMapper;
	private final String exchangeName;
	
	public HrEventRabbitService(
			@Value("${exchangeName}") String exchangeName,
			RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
		this.rabbitTemplate = rabbitTemplate;
		this.objectMapper = objectMapper;
		this.exchangeName = exchangeName;
	}

	@EventListener
	public void publishEvent(HrDomainEvent event) {
		try {
			var eventAsJson = objectMapper.writeValueAsString(event);
			rabbitTemplate.convertAndSend(exchangeName, null, eventAsJson);
		} catch (JsonProcessingException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
