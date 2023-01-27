package com.example.crm.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventListener {

	@KafkaListener(topics="es-customers", groupId = "es-customers-listener")
	public void listen(String event) {
		//TODO
	}
}
