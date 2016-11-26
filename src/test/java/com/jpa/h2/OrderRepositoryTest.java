package com.jpa.h2;

import java.math.BigDecimal;

import org.junit.Assert;
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

import com.jpa.h2.entity.Order;
import com.jpa.h2.entity.Product;
import com.jpa.h2.repository.IOrderRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TestExecutionListeners({ 
	DependencyInjectionTestExecutionListener.class, 
	DirtiesContextTestExecutionListener.class,
	TransactionalTestExecutionListener.class 
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderRepositoryTest {
	
	Logger LOG = LoggerFactory.getLogger(OrderRepositoryTest.class);
	
	@Autowired
	private IOrderRepository orderRepository;
	String codeOrder = "1";
	
	@Test
	public void a_create_order() {
		LOG.info("test create order by code {}", codeOrder);
		
		Order order = new Order(codeOrder);

		Product product1 = new Product("bike", BigDecimal.TEN);
		Product product2 = new Product("shoes", BigDecimal.TEN);

		order.addProduct(product1);
		order.addProduct(product2);

		orderRepository.save(order);
		
		Assert.assertEquals(order.getId() != null, true);
	}
	
	@Test
    public void b_find_order() {
		LOG.info("test find order by code {}", codeOrder);
		
    	Order order = orderRepository.findByCode(codeOrder);

    	Assert.assertEquals(order.getCode(), codeOrder);
    }
	
	@Test
    public void c_update_order() {
		LOG.info("test update order by code {}", codeOrder);
		
		Order order = orderRepository.findByCode(codeOrder);
    	
		order.productsClear();
		
		Product product = new Product("bike", BigDecimal.ONE);
		order.addProduct(product);
		
		orderRepository.save(order);
    	
    	order = orderRepository.findByCode(codeOrder);

    	Assert.assertEquals(order.getProducts().size(), 1);
    }
	
	@Test
    public void d_remove_order() {
		LOG.info("test remove order by code {}", codeOrder);
		
		Order order = orderRepository.findByCode(codeOrder);
    	
		orderRepository.delete(order);
    }
}