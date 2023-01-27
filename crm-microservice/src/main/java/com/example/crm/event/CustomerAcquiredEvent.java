package com.example.crm.event;

import com.example.crm.document.CustomerDocument;

import lombok.Data;

@Data
public class CustomerAcquiredEvent extends CustomerBaseEvent {
	private CustomerDocument customer;
}
