package com.example.om.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.example.om.entity.Order;
import com.example.om.service.SagaCoordinator;

@RestController
@RequestMapping("/orders")
@RequestScope
@CrossOrigin
public class OrderManagementController {

	private final SagaCoordinator sagaCoordinator;

	public OrderManagementController(SagaCoordinator sagaCoordinator) {
		this.sagaCoordinator = sagaCoordinator;
	}

	@PostMapping
	public Order createOrder(@RequestBody Order order) throws Exception {
		return sagaCoordinator.createOrder(order);
	}
}
