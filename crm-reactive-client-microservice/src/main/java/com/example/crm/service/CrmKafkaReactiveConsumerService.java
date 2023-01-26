package com.example.crm.service;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class CrmKafkaReactiveConsumerService {
	private final ReactiveKafkaConsumerTemplate<String, String> reactiveKafkaConsumerTemplate;

	public CrmKafkaReactiveConsumerService(
			ReactiveKafkaConsumerTemplate<String, String> reactiveKafkaConsumerTemplate) {
		this.reactiveKafkaConsumerTemplate = reactiveKafkaConsumerTemplate;
	}

	private Flux<String> consume(){
		return reactiveKafkaConsumerTemplate.receiveAutoAck()
				                            .doOnNext( record -> System.out.println("received: %s".formatted(record.value())))
				                            .map(ConsumerRecord::value);
	}
	
	
	@PostConstruct
	public void init() {
		consume().subscribe();
	}
}
