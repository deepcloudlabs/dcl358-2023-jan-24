package com.example.ecommerce.document;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.ecommerce.entity.BookItem;

import lombok.Data;

@Data
@Document(collection="orders")
public class Order {
	@Id
	private int orderId;
	private int userId;
	private List<BookItem> items;
	private double total;
}
