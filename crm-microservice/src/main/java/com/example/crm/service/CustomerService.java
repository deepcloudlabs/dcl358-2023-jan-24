package com.example.crm.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Objects;

import org.apache.kafka.common.Uuid;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.crm.document.Address;
import com.example.crm.document.CustomerDocument;
import com.example.crm.event.CustomerAcquiredEvent;
import com.example.crm.event.CustomerNewAdressAddedEvent;
import com.example.crm.event.CustomerReleasedEvent;
import com.example.crm.repository.CustomerDocumentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CustomerService {
	private final CustomerDocumentRepository customerDocumentRepository;
	private final KafkaTemplate<String,String> kafkaTemplate;
	private final ObjectMapper objectMapper;
	
	public CustomerService(CustomerDocumentRepository customerDocumentRepository,
			KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
		this.customerDocumentRepository = customerDocumentRepository;
		this.kafkaTemplate = kafkaTemplate;
		this.objectMapper = objectMapper;
	}

	public CustomerDocument findCustomerByIdentity(String identity) {
		return customerDocumentRepository.findById(identity).orElseThrow();
	}

	public CustomerDocument addCustomer(CustomerDocument customer) throws JsonProcessingException {
		var insertedCustomer = customerDocumentRepository.insert(customer);
		var event = new CustomerAcquiredEvent();
		event.setCustomer(customer);
		event.setIdentity(customer.getIdentity());
		event.setEventId(Uuid.randomUuid().toString());
		event.setTimestamp(ZonedDateTime.now().toEpochSecond());
		var eventAsJson = objectMapper.writeValueAsString(event);
		kafkaTemplate.send("es-customers", eventAsJson);
		return insertedCustomer;
		
	}

	public Address addAddress(String identity, Address address) throws JsonProcessingException {
		var cust = customerDocumentRepository.findById(identity);
		var customer = cust.orElseThrow();
		var addresses = customer.getAddresses();
		if (Objects.isNull(addresses))
			addresses = new ArrayList<>();
		addresses.add(address);
		customer.setAddresses(addresses);
		customerDocumentRepository.save(customer);
		var event = new CustomerNewAdressAddedEvent();
		event.setAddress(address);
		event.setIdentity(identity);
		event.setEventId(Uuid.randomUuid().toString());
		event.setTimestamp(ZonedDateTime.now().toEpochSecond());
		var eventAsJson = objectMapper.writeValueAsString(event);
		kafkaTemplate.send("es-customers", eventAsJson);
		return address;
	}

	public CustomerDocument removeByIdentity(String identity) throws JsonProcessingException {
		var cust = customerDocumentRepository.findById(identity);
		var customer = cust.orElseThrow();
		customerDocumentRepository.delete(customer);
		var event = new CustomerReleasedEvent();
		event.setIdentity(identity);
		event.setEventId(Uuid.randomUuid().toString());
		event.setTimestamp(ZonedDateTime.now().toEpochSecond());
		var eventAsJson = objectMapper.writeValueAsString(event);
		kafkaTemplate.send("es-customers", eventAsJson);		
		return customer;
	}

}
