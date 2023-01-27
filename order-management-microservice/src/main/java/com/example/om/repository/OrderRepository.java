package com.example.om.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.om.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
