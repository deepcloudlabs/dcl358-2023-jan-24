package com.example.om.dto.message;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryMessage {
	private long orderId;
	private List<InventoryItem> items;
}
