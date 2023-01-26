package com.example.ecommerce.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.ecommerce.document.Order;
import com.example.ecommerce.document.OrderStatistics;

public interface OrderStatisticsRepository extends MongoRepository<OrderStatistics, String>{

}
