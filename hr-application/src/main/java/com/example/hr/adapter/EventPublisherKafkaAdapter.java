package com.example.hr.adapter;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.hr.domain.event.HrDomainEvent;
import com.example.hr.infrastructure.EventPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.resilience4j.retry.annotation.Retry;

@Service
@ConditionalOnProperty(name = "messaging", havingValue = "kafka")
public class EventPublisherKafkaAdapter implements EventPublisher<HrDomainEvent> {
	private final KafkaTemplate<String, String> kafkaTemplate;
	private final ObjectMapper objectMapper;
	private final String topicName;

	public EventPublisherKafkaAdapter(@Value("${topicName}") String topicName,
			KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
		this.kafkaTemplate = kafkaTemplate;
		this.objectMapper = objectMapper;
		this.topicName = topicName;
	}

	@Override
	@Retry(name = "kafkaRetry", fallbackMethod = "publishEventFallback")
	public void publishEvent(HrDomainEvent event) {
		try {
			var eventAsJson = objectMapper.writeValueAsString(event);
			kafkaTemplate.send(topicName, eventAsJson);
		} catch (JsonProcessingException e) {
			System.out.println(e.getMessage());
		}

	}

	public void publishEventFallback(HrDomainEvent event, Throwable t) {
		Arrays.stream(t.getSuppressed()).forEach(System.err::println);
		System.out.println("Outbox pattern is executed for event %s and exception %s".formatted(event, t.getMessage()));
	}
}
