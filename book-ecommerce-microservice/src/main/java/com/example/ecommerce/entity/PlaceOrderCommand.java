package com.example.ecommerce.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
public class PlaceOrderCommand extends OrderCommand {
	private int userId;
	private List<BookItem> items;
}
