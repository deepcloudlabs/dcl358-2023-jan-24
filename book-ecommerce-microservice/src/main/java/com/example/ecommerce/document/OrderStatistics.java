package com.example.ecommerce.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "statistics")
public class OrderStatistics {
	@Id
	private String name;
	private int orderCount;
	private double total;
	
}
