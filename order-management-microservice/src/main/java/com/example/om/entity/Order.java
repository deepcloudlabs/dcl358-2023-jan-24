package com.example.om.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long orderId;
    @OneToMany(cascade = {CascadeType.PERSIST},fetch = FetchType.EAGER)
    private List<OrderItem> items = new ArrayList<>();
    private double total;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private String customerId;
    
    public void addOrderItem(OrderItem item) {
    	items.add(item);
    	total += item.getSubTotal();
    }
}
