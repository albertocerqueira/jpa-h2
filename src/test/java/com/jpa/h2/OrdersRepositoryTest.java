package com.jpa.h2;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
public class OrdersRepositoryTest {

	@Autowired
	private IOrderRepository orderRepository;

	private String[] products = new String[] { "car", "bike", "bus", "shoes", "pencil", "pen", "tv", "phone",
			"notabook", "pc", "mouse", "window", "paper", "key", "shirt", "pain", "notepad", "erase", "backpack",
			"glass", "door", "table", "mirror", "fan", "clock", "ball", "blouse", "sock", "hat", "umbrella", "float",
			"wheel", "skateboard", "roller skates", "video game", "cell phone" };

	@Test
	public void a_create_orders() {
		for (int x = 0, y = products.length; x < y; x++) {
			String p = products[x];
			Order order = new Order(x + 1 + "");

			Product product = new Product(p, BigDecimal.TEN);

			order.addProduct(product);

			orderRepository.save(order);
		}
	}

	@Test
	public void b_find_products() {
		List<Order> orders = orderRepository.listOrders();

		Assert.assertEquals(orders.size(), this.products.length);
	}
}