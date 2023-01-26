package com.example.ecommerce.service;

import java.util.List;

import com.example.ecommerce.entity.BookItem;

public interface OrderCommandService {
	void placeOrder(int userId, List<BookItem> items) throws Exception;

	void cancelOrder(int userId, int orderId) throws Exception;
}
