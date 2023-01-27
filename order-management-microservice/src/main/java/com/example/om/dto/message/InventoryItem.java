package com.example.om.dto.message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryItem {
	private String sku;
	private double quantity;
}
