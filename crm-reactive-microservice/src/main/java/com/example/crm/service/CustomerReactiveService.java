package com.example.crm.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;

import com.example.crm.document.CustomerDocument;
import com.example.crm.repository.CustomerDocumentReactiveRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerReactiveService {
	private final CustomerDocumentReactiveRepository customerDocumentReactiveRepository;
	private final ReactiveKafkaProducerTemplate<String, String> reactiveKafkaProducerTemplate;
	private final ObjectMapper objectMapper;
	

	public CustomerReactiveService(CustomerDocumentReactiveRepository customerDocumentReactiveRepository,
			ReactiveKafkaProducerTemplate<String, String> reactiveKafkaProducerTemplate, ObjectMapper objectMapper) {
		this.customerDocumentReactiveRepository = customerDocumentReactiveRepository;
		this.reactiveKafkaProducerTemplate = reactiveKafkaProducerTemplate;
		this.objectMapper = objectMapper;
	}

	public Mono<CustomerDocument> findCustomerById(String identity) {
		return customerDocumentReactiveRepository.findById(identity);
	}

	public Flux<CustomerDocument> findAllByPage(int page, int size) {
		return customerDocumentReactiveRepository.findAll(PageRequest.of(page, size));
	}

	public Mono<CustomerDocument> acquire(CustomerDocument customer) {
		try {
			var customerAsJson = objectMapper.writeValueAsString(customer);
			return customerDocumentReactiveRepository.insert(customer)
					.doOnNext(cust -> reactiveKafkaProducerTemplate.send("crm-events", customerAsJson).subscribe());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return Mono.empty();
	}

	public Mono<CustomerDocument> updateCustomer(String identity, CustomerDocument customer) {
		return customerDocumentReactiveRepository.findById(identity)
				.doOnNext(cust -> customerDocumentReactiveRepository.save(customer).subscribe());
	}

	public Mono<CustomerDocument> releaseCustomerById(String identity) {
		return customerDocumentReactiveRepository.findById(identity)
				.doOnNext(customer -> customerDocumentReactiveRepository.delete(customer).subscribe());
	}

}
