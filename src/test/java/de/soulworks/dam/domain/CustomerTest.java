package de.soulworks.dam.domain;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
public class CustomerTest {
	@Test
	public void testConstruct() {
		Customer customer = new Customer();
		Assert.assertEquals("Customer", customer.getClass().getSimpleName());
	}

	@Test
	public void testCreate() {
		Customer customer = Customer.create();
		Assert.assertEquals("Customer", customer.getClass().getSimpleName());
	}

	@Test
	public void testSetAndGetName() {
		Customer customer = Customer.create().setName("name");
		Assert.assertEquals("name", customer.getName());
	}

	@Test
	public void testSetAndGetUid() {
		Customer customer = Customer.create().setUid("ide");
		Assert.assertEquals("ide", customer.getUid());
	}
}
