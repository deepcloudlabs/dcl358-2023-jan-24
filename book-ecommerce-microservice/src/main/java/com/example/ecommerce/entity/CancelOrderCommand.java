package com.example.ecommerce.entity;

import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class CancelOrderCommand extends OrderCommand {
	private int userId;
	private int orderId;
}
