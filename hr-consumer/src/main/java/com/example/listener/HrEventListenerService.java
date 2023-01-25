package com.example.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.dto.HrEventDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class HrEventListenerService {
	private final ObjectMapper objectMapper;
	
	public HrEventListenerService(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@KafkaListener(topics = "${topicName}", groupId = "${spring.application.name}")
	public void listenKafkaHrEvent(String eventAsString) throws Exception {
		var hrEvent = objectMapper.readValue(eventAsString, HrEventDTO.class);
		System.err.println("New hr event has arrived from kafka: %s.".formatted(hrEvent));
		var restTemplate = new RestTemplate();
		var employee = restTemplate.getForEntity("http://localhost:4001/hr/api/v1/employees/%s".formatted(hrEvent.getIdentityNo().getValue()), String.class).getBody();
		System.out.println(employee);
	}
	
	@RabbitListener(queues = "${queueName}")
	public void listenRabbitHrEvent(String eventAsString) throws Exception {
		var hrEvent = objectMapper.readValue(eventAsString, HrEventDTO.class);
		System.err.println("New hr event has arrived from rabbit: %s.".formatted(hrEvent));
		var restTemplate = new RestTemplate();
		var employee = restTemplate.getForEntity("http://localhost:4001/hr/api/v1/employees/%s".formatted(hrEvent.getIdentityNo().getValue()), String.class).getBody();
		System.out.println(employee);
	}
}
