package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.entity.OrderCommand;

public interface OrderCommandRepository extends JpaRepository<OrderCommand, Long>{

}
