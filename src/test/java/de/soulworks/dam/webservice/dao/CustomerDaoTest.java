package de.soulworks.dam.webservice.dao;

import de.soulworks.dam.domain.Customer;
import org.joda.time.Instant;
import org.joda.time.ReadableInstant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Christian Schmitz <csc@soulworks.de
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/ektorp-config.xml")
public class CustomerDaoTest {
	@Autowired
	@Qualifier("customerDao")
	CustomerDao customerDao;

	@Test
	public void testGetCustomer() {
		ReadableInstant now = new Instant();
		String uid = "testCreateCustomer." + now.getMillis();

		// Compose a customer
		Customer customer = Customer.create()
				.setUid(uid)
				.setName("Soulworks GmbH");

		// Create the customer at the datastore
		customerDao.createCustomer(customer);

		// Get the same customer from the datastore
		Customer c = customerDao.getCustomer(uid);

		Assert.assertNotNull(c);
	}

	public void testGetCustomers() {

	}

	@Test
	public void testCreateCustomer() {
		ReadableInstant now = new Instant();
		String uid = "testCreateCustomer." + now.getMillis();

		// Compose a customer
		Customer customer = Customer.create()
				.setUid(uid)
				.setName("Company GmbH");

		// Create the customer at the datastore
		customerDao.createCustomer(customer);

		// Check if we got an id and revision after creation
		Assert.assertNotNull(customer.getId());
		Assert.assertNotNull(customer.getRevision());

		// Get the same customer from the datastore
		Customer c = customerDao.getCustomer(uid);

		// Check wether we got a customer or not
		Assert.assertNotNull(c);

		// Compare the objects
		Assert.assertEquals(customer.getUid(), c.getUid());
		Assert.assertEquals(customer.getName(), c.getName());

		Assert.assertTrue(customer.equals(c));
	}

	@Test
	public void testUpdateCustomer() {
		ReadableInstant now = new Instant();
		String uid = "testUpdateCustomer." + now.getMillis();

		// Compose a customer
		Customer customer = Customer.create()
				.setUid(uid)
				.setName("Company GmbH");

		// Create the customer at the datastore
		customerDao.createCustomer(customer);

		// Get the same customer from the datastore
		customer.setName("Jockel GmbH");

		customerDao.updateCustomer(customer);

		// Get the same customer from the datastore
		Customer c = customerDao.getCustomer(uid);

		Assert.assertEquals("Jockel GmbH", c.getName());
	}

	public void testDeleteCustomer() {

	}
}
