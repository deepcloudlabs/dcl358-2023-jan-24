package com.example.crm.event;

import com.example.crm.document.Address;

import lombok.Data;

@Data
public class CustomerNewAdressAddedEvent extends CustomerBaseEvent {
	private Address address;
}
