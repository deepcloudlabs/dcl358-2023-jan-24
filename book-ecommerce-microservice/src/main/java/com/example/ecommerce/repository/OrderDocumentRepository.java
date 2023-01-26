package com.example.ecommerce.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.ecommerce.document.Order;

public interface OrderDocumentRepository extends MongoRepository<Order, Integer>{

}
