package com.jpa.h2;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
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
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
public class ProductRepositoryTest {

	@Autowired
	private IProductRepository productRepository;
	private EmbeddedDatabase db;

	@Before
	public void setUp() {
		// db = new EmbeddedDatabaseBuilder().addDefaultScripts().build();
		db = new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript("db/sql/PRODUCT.sql")
				.addScript("db/sql/PRODUCT-INSERTS.sql")
				.build();
	}

	// @Test
	public void create_product() {
		String nameProduct = "necklace";

		productRepository.save(new Product("necklace", BigDecimal.TEN, "great product"));
		
		Product product = productRepository.findByName(nameProduct);
		
		Assert.assertEquals(product.getName(), nameProduct);
	}
	
	@Test
    public void find_product() {
		String nameProduct = "pen";
		
    	Product product = productRepository.findByName(nameProduct);

    	Assert.assertEquals(product.getName(), nameProduct);
    }
	
	@After
    public void tearDown() {
        db.shutdown();
    }
}