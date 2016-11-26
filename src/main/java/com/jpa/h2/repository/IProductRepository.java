package com.jpa.h2.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.jpa.h2.entity.Image;
import com.jpa.h2.entity.Product;

public interface IProductRepository extends Repository<Product, Long> {
	
	Product save(Product product);
	void save(List<Product> products);
	void delete(Product product);
	List<Product> listProducts();
	Product findById(Long id);
	Product findByName(String name);
	
	Image save(Product product, Image image);
	void save(Product product, List<Image> images);
	void delete(Image image);
}