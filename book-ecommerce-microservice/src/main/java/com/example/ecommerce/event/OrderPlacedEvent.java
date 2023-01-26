package com.example.ecommerce.event;

import java.util.List;

import com.example.ecommerce.entity.BookItem;

import lombok.Data;

@Data
public class OrderPlacedEvent extends OrderEvent{
	private List<BookItem> items;
}
