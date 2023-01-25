package com.example.hr.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class HrDomainEvent {
	private final String id = UUID.randomUUID().toString();
	private final LocalDateTime timestamp = LocalDateTime.now();

	public String getId() {
		return id;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

}
