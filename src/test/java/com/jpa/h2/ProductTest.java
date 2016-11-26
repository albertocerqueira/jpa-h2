package com.jpa.h2;

import org.junit.Assert;
import org.junit.Test;

import com.jpa.h2.entity.Product;

public class ProductTest {

	@Test
	public void new_product() {
		try {
			new Product(null, null);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			
		}
	}
}