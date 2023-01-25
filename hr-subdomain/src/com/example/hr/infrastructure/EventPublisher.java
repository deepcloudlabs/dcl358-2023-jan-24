package com.example.hr.infrastructure;

public interface EventPublisher<Event> {
	public void publishEvent(Event event);
}
