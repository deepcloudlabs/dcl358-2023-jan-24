package com.example.payment.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentMessage {
	private String customerId;
	private long orderId;
	private double total;
}
