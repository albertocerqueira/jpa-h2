package com.jpa.h2;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.jpa.h2.entity.Product;
import com.jpa.h2.repository.IProductRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TestExecutionListeners({ 
	DependencyInjectionTestExecutionListener.class, 
	DirtiesContextTestExecutionListener.class,
	TransactionalTestExecutionListener.class
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductRepositoryTest {
	
	Logger LOG = LoggerFactory.getLogger(ProductRepositoryTest.class);
	
	@Autowired
	private IProductRepository productRepository;
	String nameProduct = "pen";
	
	@Before
	public void setUp() {
		// productRepository.createTable();
	}
	
	@Test
	public void a_create_product() {
		LOG.info("test create product by name {}", nameProduct);
		
		Product product = new Product(nameProduct, BigDecimal.TEN, "great product");
		
		productRepository.save(product);
		
		Assert.assertEquals(product.getId() != null, true);
	}
	
	@Test
    public void b_find_product() {
		LOG.info("test find product by name {}", nameProduct);
		
    	Product product = productRepository.findByName(nameProduct);

    	Assert.assertEquals(product.getName(), nameProduct);
    }
	
	@Test
    public void c_update_product() {
		LOG.info("test update product by name {}", nameProduct);
		
    	Product product = productRepository.findByName(nameProduct);
    	
    	product.setDescription("product great");
    	
    	productRepository.save(product);
    	
    	product = productRepository.findByName(nameProduct);

    	Assert.assertEquals(product.getDescription(), "product great");
    }
	
	@Test
    public void d_remove_product() {
		LOG.info("test remove product by name {}", nameProduct);
		
    	Product product = productRepository.findByName(nameProduct);
    	
    	productRepository.delete(product);
    }
	
	@After
	public void cleanup() {
	    // productRepository.dropTable();
	}
}