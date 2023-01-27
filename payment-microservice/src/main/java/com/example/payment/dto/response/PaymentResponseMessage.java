package com.example.payment.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponseMessage {
	private long orderId;
	private String status;
}
