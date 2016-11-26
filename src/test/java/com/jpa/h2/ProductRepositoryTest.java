package com.jpa.h2;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
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
public class ProductRepositoryTest {
	
	Logger LOG = LoggerFactory.getLogger(ProductRepositoryTest.class);
	
	@Autowired
	private IProductRepository productRepository;
	String nameProduct = "pen";
	
	//private EmbeddedDatabase db;
/*
	@Before
	public void setUp() {
		// db = new EmbeddedDatabaseBuilder().addDefaultScripts().build();
		db = new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript("db/sql/PRODUCT.sql")
				.addScript("db/sql/PRODUCT-INSERTS.sql")
				.build();
	}
*/
	@Test
	@Order(value = 1)
	public void create_product() {
		LOG.info("test create product by name {}", nameProduct);
		
		Product product = new Product(nameProduct, BigDecimal.TEN, "great product");
		
		productRepository.save(product);
		
		Assert.assertEquals(product.getId() != null, true);
	}
	
	@Test
	@Order(value = 2)
    public void find_product() {
		LOG.info("test find product by name {}", nameProduct);
		
    	Product product = productRepository.findByName(nameProduct);

    	Assert.assertEquals(product.getName(), nameProduct);
    }
	
	@Test
	@Order(value = 3)
    public void update_product() {
		LOG.info("test update product by name {}", nameProduct);
		
    	Product product = productRepository.findByName(nameProduct);
    	
    	product.setDescription("product great");
    	
    	productRepository.save(product);
    	
    	product = productRepository.findByName(nameProduct);

    	Assert.assertEquals(product.getName(), "product great");
    }
	
	@Test
	@Order(value = 4)
    public void remove_product() {
		LOG.info("test remove product by name {}", nameProduct);
		
    	Product product = productRepository.findByName(nameProduct);
    	
    	productRepository.delete(product);
    }
	/*
	@After
    public void tearDown() {
        db.shutdown();
    }
    */
}