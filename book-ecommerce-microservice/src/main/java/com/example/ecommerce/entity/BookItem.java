package com.example.ecommerce.entity;

import lombok.Data;

@Data
public class BookItem {
	private long bookItemId;
	private String isbn;
	private double price;
	private int quantity; 
}
