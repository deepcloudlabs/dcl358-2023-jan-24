package com.example.payment.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.payment.dto.request.PaymentMessage;
import com.example.payment.dto.response.PaymentResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PaymentService {
	private final KafkaTemplate<String,String> kafkaTemplate;
	private final ObjectMapper objectMapper;
	
	public PaymentService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
		this.kafkaTemplate = kafkaTemplate;
		this.objectMapper = objectMapper;
	}

	@KafkaListener(topics="order-payment")
	public void listenPaymentMessage(String paymentMesssage) throws Exception {
		var paymentRequest = objectMapper.readValue(paymentMesssage, PaymentMessage.class);
		var paymentResponseMesssage = PaymentResponseMessage.builder().status("ok")
				                                            .orderId(paymentRequest.getOrderId());
		kafkaTemplate.send("order-payment-response", objectMapper.writeValueAsString(paymentResponseMesssage));
	}
}
