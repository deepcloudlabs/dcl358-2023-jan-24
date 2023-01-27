package com.example.om.dto.message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryResponseMessage {
	private long orderId;
	private InventoryStatus inventoryStatus;
}
