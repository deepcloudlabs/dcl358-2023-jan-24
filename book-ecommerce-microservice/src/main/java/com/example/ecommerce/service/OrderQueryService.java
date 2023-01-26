package com.example.ecommerce.service;

import java.util.List;

import com.example.ecommerce.document.OrderStatistics;
import com.example.ecommerce.entity.BookItem;

public interface OrderQueryService {
	List<BookItem> getOrder(int orderId);
	OrderStatistics getPurchaseOrderSummary();
}
