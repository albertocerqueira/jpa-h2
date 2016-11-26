package com.jpa.h2;

import org.junit.Assert;
import org.junit.Test;

import com.jpa.h2.entity.Image;

public class ImageTest {

	@Test
	public void new_image() {
		try {
			new Image(null, null);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			
		}
	}
}