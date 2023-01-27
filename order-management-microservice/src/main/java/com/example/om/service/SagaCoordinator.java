package com.example.om.service;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.om.dto.message.InventoryItem;
import com.example.om.dto.message.InventoryMessage;
import com.example.om.dto.message.InventoryResponseMessage;
import com.example.om.dto.message.InventoryStatus;
import com.example.om.dto.message.Payment;
import com.example.om.dto.message.PaymentResponseMessage;
import com.example.om.entity.Order;
import com.example.om.entity.OrderStatus;
import com.example.om.repository.OrderRepository;
import com.example.om.saga.Compansation;
import com.example.om.saga.OrderAction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SagaCoordinator {

	private final OrderRepository orderRepository;
	private final KafkaTemplate<String, String> kafkaTemplate;
	private final ObjectMapper objectMapper;
	
	public SagaCoordinator(OrderRepository orderRepository, KafkaTemplate<String, String> kafkaTemplate,
			ObjectMapper objectMapper) {
		this.orderRepository = orderRepository;
		this.kafkaTemplate = kafkaTemplate;
		this.objectMapper = objectMapper;
	}

	@Transactional
	public Order createOrder(Order order) throws Exception {
		order.setStatus(OrderStatus.CREATED);
		var savedOrder = orderRepository.save(order);
		var payment= Payment.builder()
				.customerId(order.getCustomerId())
				.orderId(order.getOrderId())
				.total(order.getTotal())
				.build();
		kafkaTemplate.send("order-payment", objectMapper.writeValueAsString(payment));
		return savedOrder;		
	}

	@Transactional
	@KafkaListener(topics="order-payment-response")
	public void listenPaymentResponseMessage(String paymentResponseMesssage) throws Exception { 
		var responseMessage= objectMapper.readValue(paymentResponseMesssage, PaymentResponseMessage.class);
		orderRepository.findById(responseMessage.getOrderId())
		               .ifPresent(order -> {
		            	   if (order.getStatus() == OrderStatus.CREATED) {
		            		   try {
		            			   order.setStatus(OrderStatus.PAYMENT);
		            			   orderRepository.save(order);
		            			   List<InventoryItem> items = order.getItems().stream().map(item -> InventoryItem.builder().sku(item.getSku()).quantity(item.getQuantity()).build()).toList();
		            			   var inventoryMessage = InventoryMessage.builder()
		            					   .orderId(order.getOrderId())
		            					   .items(items)
		            					   .build();
								kafkaTemplate.send("order-inventory", objectMapper.writeValueAsString(inventoryMessage));
							} catch (JsonProcessingException e) {
								System.err.println(e.getMessage());
							}		            		   
		            	   }
		               });
	}

	@Transactional
	@KafkaListener(topics="order-inventory-response")
	public void listenInventoryResponseMessage(String inventoryResponseMesssage) throws Exception { 
		var responseMessage= objectMapper.readValue(inventoryResponseMesssage, InventoryResponseMessage.class);
		orderRepository.findById(responseMessage.getOrderId())
		.ifPresent(order -> {
			if (order.getStatus() == OrderStatus.PAYMENT ) {
				if (responseMessage.getInventoryStatus() == InventoryStatus.IN_STOCK)
					order.setStatus(OrderStatus.SENT);
			    else if (responseMessage.getInventoryStatus() == InventoryStatus.NOT_IN_STOCK) {
			    	order.setStatus(OrderStatus.NOT_IN_STOCK);
			    }
				orderRepository.save(order);
			}
		});
	}
	

	@Compansation(action=OrderAction.VALIDATE_PAYMENT)
	public void cancelPayment() {
		// TODO: cancel payment
	}

	@Compansation(action=OrderAction.DROP_FROM_INVENTORY)
	public void cancelInventory(long orderId) {
		//TODO: add to inventory
	}

	@Compansation(action=OrderAction.CREATE_ORDER)
	public void cancelOrder(long orderId) {
		Consumer<Order> changeOrderStatusToCanceled = order -> order.setStatus(OrderStatus.CANCELED);
		Consumer<Order> saveOrder = order -> orderRepository.save(order);
		orderRepository.findById(orderId)
				       .ifPresent( changeOrderStatusToCanceled.andThen(saveOrder) );
	}
}
