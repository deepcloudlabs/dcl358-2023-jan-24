package com.example.ecommerce.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Data;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
		@Type(value = OrderPlacedEvent.class, name = "orderPlaced"),
		@Type(value = OrderCancelledEvent.class, name = "orderCanceled")
})
public abstract class OrderEvent {
	private int orderId;
	private int userId;
}
