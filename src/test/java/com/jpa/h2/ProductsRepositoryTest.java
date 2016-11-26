package com.jpa.h2;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
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
public class ProductsRepositoryTest {

	@Autowired
	private IProductRepository productRepository;

	private String[] products = new String[] { "car", "bike", "bus", "shoes", "pencil", "pen", "tv", "phone",
			"notabook", "pc", "mouse", "window", "paper", "key", "shirt", "pain", "notepad", "erase", "backpack",
			"glass", "door", "table", "mirror", "fan", "clock", "ball", "blouse", "sock", "hat", "umbrella", "float",
			"wheel", "skateboard", "roller skates", "video game", "cell phone" };

	@Test
	public void create_products_and_test() {
		for (String p : products) {
			Product product = new Product(p, BigDecimal.TEN, "great product");
			productRepository.save(product);
		}
	}
}