package com.example.ecommerce.service.business;

import java.util.Optional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.ecommerce.document.Order;
import com.example.ecommerce.document.OrderStatistics;
import com.example.ecommerce.event.OrderCancelledEvent;
import com.example.ecommerce.event.OrderEvent;
import com.example.ecommerce.event.OrderPlacedEvent;
import com.example.ecommerce.repository.OrderStatisticsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@ConditionalOnProperty(name = "enable.read", havingValue = "true")
public class OrderStatisticsProjectionService {
	private final ObjectMapper objectMapper;
	private final OrderStatisticsRepository orderStatisticsRepository;

	public OrderStatisticsProjectionService(ObjectMapper objectMapper, OrderStatisticsRepository orderStatisticsRepository) {
		this.objectMapper = objectMapper;
		this.orderStatisticsRepository = orderStatisticsRepository;
	}

	@KafkaListener(topics = "orders", groupId = "order-statistics-read-model")
	public void listenEvent(String eventAsString) throws JsonMappingException, JsonProcessingException {
		System.out.println("received event: %s".formatted(eventAsString));
		Optional<OrderStatistics> orderStatistics = orderStatisticsRepository.findById("statistics");
		OrderStatistics orderStat = null;
		if (orderStatistics.isEmpty()) {
			orderStat = new OrderStatistics();
			orderStat.setName("statistics");
			orderStatisticsRepository.insert(orderStat);
		} else {
			orderStat = orderStatistics.get();
		}
		var receivedEvent = objectMapper.readValue(eventAsString, OrderEvent.class);
		if (receivedEvent instanceof OrderPlacedEvent event) {
			var total = event.getItems().stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
			orderStat.setTotal(orderStat.getTotal()+total);
			orderStat.setOrderCount(orderStat.getOrderCount()+1);
			orderStatisticsRepository.save(orderStat);
		} else if (receivedEvent instanceof OrderCancelledEvent event) {
			//TODO	
		}
	}
}
