package com.jpa.h2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

/**
 * 
 * super test class, just run
 * 
 * @author albertocerqueira
 *
 */
@Suite.SuiteClasses({ 
	ImageTest.class,
	ProductTest.class,
	ProductRepositoryTest.class, 
	ProductsRepositoryTest.class,
	OrderRepositoryTest.class, 
	OrdersRepositoryTest.class
})
public class JunitTestSuite {

	
}