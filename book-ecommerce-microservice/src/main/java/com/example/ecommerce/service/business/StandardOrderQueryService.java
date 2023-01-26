package com.example.ecommerce.service.business;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.example.ecommerce.document.OrderStatistics;
import com.example.ecommerce.entity.BookItem;
import com.example.ecommerce.repository.OrderDocumentRepository;
import com.example.ecommerce.repository.OrderStatisticsRepository;
import com.example.ecommerce.service.OrderQueryService;

@Service
@ConditionalOnProperty(name="enable.read",havingValue = "true")
public class StandardOrderQueryService implements OrderQueryService {
	private final OrderDocumentRepository orderDocumentRepository;
	private final OrderStatisticsRepository orderStatisticsRepository;
	
	public StandardOrderQueryService(OrderDocumentRepository orderDocumentRepository,
			OrderStatisticsRepository orderStatisticsRepository) {
		this.orderDocumentRepository = orderDocumentRepository;
		this.orderStatisticsRepository = orderStatisticsRepository;
	}

	@Override
	public List<BookItem> getOrder(int orderId) {
		return orderDocumentRepository.findById(orderId).orElseThrow(()->new IllegalArgumentException("Cannot find order")).getItems();
	}

	@Override
	public OrderStatistics getPurchaseOrderSummary() {
		return orderStatisticsRepository.findById("statistics").orElseThrow();
	}

}
