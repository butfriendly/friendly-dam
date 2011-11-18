package de.soulworks.dam.webservice.dao;

import de.soulworks.dam.domain.Customer;

import java.util.List;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
public interface CustomerDao {
	public Customer getCustomer(String customerUid);

	public List<Customer> getCustomers();

	public void createCustomer(Customer customer);

	public void updateCustomer(Customer customer);

	public void deleteCustomer(Customer customer);
}
