package com.example.ecommerce.service.business;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.ecommerce.entity.BookItem;
import com.example.ecommerce.entity.CancelOrderCommand;
import com.example.ecommerce.entity.PlaceOrderCommand;
import com.example.ecommerce.event.OrderCancelledEvent;
import com.example.ecommerce.event.OrderPlacedEvent;
import com.example.ecommerce.repository.OrderCommandRepository;
import com.example.ecommerce.service.OrderCommandService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@ConditionalOnProperty(name = "enable.write", havingValue = "true")
public class StandardOrderCommandService implements OrderCommandService {
	private final OrderCommandRepository orderCommandRepository;
	private final KafkaTemplate<String,String> kafkaTemplate;
	private final ObjectMapper objectMapper;
	
	public StandardOrderCommandService(OrderCommandRepository orderCommandRepository,
			KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
		this.orderCommandRepository = orderCommandRepository;
		this.kafkaTemplate = kafkaTemplate;
		this.objectMapper = objectMapper;
	}

	@Override
	@Transactional
	public void placeOrder(int userId, List<BookItem> items) throws Exception {
		var orderCommand = new PlaceOrderCommand();
		orderCommand.setItems(items);
		orderCommand.setUserId(userId);
		orderCommandRepository.save(orderCommand);
		var event = new OrderPlacedEvent();
		event.setItems(items);
		event.setUserId(userId);
		event.setOrderId(orderCommand.getCommandId());
		var eventAsJson = objectMapper.writeValueAsString(event);
		kafkaTemplate.send("orders",eventAsJson);
	}

	@Override
	@Transactional
	public void cancelOrder(int userId, int orderId) throws Exception {
		var orderCommand = new CancelOrderCommand();
		orderCommand.setUserId(userId);
		orderCommand.setOrderId(orderId);
		orderCommandRepository.save(orderCommand);
		var event = new OrderCancelledEvent();
		event.setUserId(userId);
		event.setOrderId(orderId);
		var eventAsJson = objectMapper.writeValueAsString(event);
		kafkaTemplate.send("orders",eventAsJson);

	}

}
