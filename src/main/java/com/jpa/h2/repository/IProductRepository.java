package com.jpa.h2.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.jpa.h2.entity.Product;

public interface IProductRepository extends Repository<Product, Long> {

	List<Product> listProducts();

	Product findById(Long id);

	Product findByName(String name);

	Product save(Product product);

	void save(List<Product> products);

	void delete(Product product);
}