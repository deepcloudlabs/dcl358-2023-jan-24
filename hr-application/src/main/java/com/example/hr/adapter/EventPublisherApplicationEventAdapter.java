package com.example.hr.adapter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.example.hr.domain.event.HrDomainEvent;
import com.example.hr.infrastructure.EventPublisher;

@Service
@ConditionalOnProperty(name = "messaging", havingValue = "kafka-websocket")
public class EventPublisherApplicationEventAdapter implements EventPublisher<HrDomainEvent> {
	private final ApplicationEventPublisher applicationEventPublisher;

	public EventPublisherApplicationEventAdapter(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Override
	public void publishEvent(HrDomainEvent event) {
		applicationEventPublisher.publishEvent(event);
	}

}
