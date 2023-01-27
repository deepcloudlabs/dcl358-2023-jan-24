package com.example.om.dto.message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponseMessage {
	private long orderId;
	private String status;
}
