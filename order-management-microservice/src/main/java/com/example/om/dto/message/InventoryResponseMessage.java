package com.example.om.dto.message;

import lombok.Data;

@Data
public class InventoryResponseMessage {
	private long orderId;
	private InventoryStatus inventoryStatus;
}
