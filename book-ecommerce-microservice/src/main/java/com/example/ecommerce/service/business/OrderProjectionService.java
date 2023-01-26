package com.example.ecommerce.service.business;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.ecommerce.document.Order;
import com.example.ecommerce.event.OrderCancelledEvent;
import com.example.ecommerce.event.OrderEvent;
import com.example.ecommerce.event.OrderPlacedEvent;
import com.example.ecommerce.repository.OrderDocumentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@ConditionalOnProperty(name = "enable.read", havingValue = "true")
public class OrderProjectionService {
	private final ObjectMapper objectMapper;
	private final OrderDocumentRepository orderRepository;

	public OrderProjectionService(ObjectMapper objectMapper, OrderDocumentRepository orderRepository) {
		this.objectMapper = objectMapper;
		this.orderRepository = orderRepository;
	}

	@KafkaListener(topics = "orders", groupId = "order-read-model")
	public void listenEvent(String eventAsString) throws JsonMappingException, JsonProcessingException {
		System.out.println("received event: %s".formatted(eventAsString));
		var receivedEvent = objectMapper.readValue(eventAsString, OrderEvent.class);
		if (receivedEvent instanceof OrderPlacedEvent event) {
			var order = new Order();
			order.setItems(event.getItems());
			order.setTotal(event.getItems().stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum());
			order.setUserId(event.getUserId());	
			order.setOrderId(event.getOrderId());	
			orderRepository.insert(order);
		} else if (receivedEvent instanceof OrderCancelledEvent event) {
			orderRepository.deleteById(event.getOrderId());	
		}
	}
}
