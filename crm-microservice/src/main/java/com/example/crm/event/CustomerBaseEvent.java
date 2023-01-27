package com.example.crm.event;

import lombok.Data;

@Data
public abstract class CustomerBaseEvent {
	private String eventId;
	private long timestamp;
	private String identity;
}
