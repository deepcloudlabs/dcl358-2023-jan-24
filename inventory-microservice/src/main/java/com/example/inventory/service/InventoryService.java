package com.example.inventory.service;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.om.dto.message.InventoryMessage;
import com.example.om.dto.message.InventoryResponseMessage;
import com.example.om.dto.message.InventoryStatus;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class InventoryService {

	private final KafkaTemplate<String,String> kafkaTemplate;
	private final ObjectMapper objectMapper;
	
	public InventoryService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
		this.kafkaTemplate = kafkaTemplate;
		this.objectMapper = objectMapper;
	}

	@KafkaListener(topics="order-inventory")
	public void listenPaymentMessage(String inventoryMessage) throws Exception {
		var inventoryRequest = objectMapper.readValue(inventoryMessage, InventoryMessage.class);
		var inventoryStatus = InventoryStatus.IN_STOCK;
		if (ThreadLocalRandom.current().nextBoolean())
			inventoryStatus = InventoryStatus.NOT_IN_STOCK;
		var inventoryResponseMessage = InventoryResponseMessage.builder()
				                                               .inventoryStatus(inventoryStatus)
				                                               .orderId(inventoryRequest.getOrderId());
		kafkaTemplate.send("order-inventory-response", objectMapper.writeValueAsString(inventoryResponseMessage));
	}
}
