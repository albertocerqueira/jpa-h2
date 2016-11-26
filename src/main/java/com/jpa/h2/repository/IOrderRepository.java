package com.jpa.h2.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.jpa.h2.entity.Order;

public interface IOrderRepository extends Repository<Order, Long> {

	List<Order> listOrders();

	Order findById(Long id);

	Order findByCode(String code);

	Order save(Order order);
	
	void delete(Order order);
}