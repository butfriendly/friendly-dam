package de.soulworks.dam.webservice.service;

import de.soulworks.dam.domain.Account;
import de.soulworks.dam.domain.Customer;
import de.soulworks.dam.domain.validation.Validateable;
import de.soulworks.dam.domain.validation.ValidationException;
import de.soulworks.dam.domain.validation.validator.compound.CustomerValidator;
import de.soulworks.dam.webservice.dao.AccountDao;
import de.soulworks.dam.webservice.dao.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The CustomerService wraps the business-logic arround all customer
 * related activities.
 *
 * @author Christian Schmitz <csc@soulworks.de>
 */
@Service
public class CustomerService {
	@Autowired
	@Qualifier("customerDao")
	private CustomerDao customerDao;

	@Autowired
	@Qualifier("accountDao")
	private AccountDao accountDao;

	/**
	 * Retrieves a single customer, identified by it's uid.
	 * 
	 * @param customerUid
	 * @return
	 */
	public Customer getCustomer(String customerUid) {
		return customerDao.getCustomer(customerUid);
	}

	/**
	 * Returns all available customers
	 * 
	 * @return
	 */
	public List<Customer> getCustomers() {
		return customerDao.getCustomers();
	}

	/**
	 * Returns all accounts of a customer, identified by the given customerUid.
	 * 
	 * @param customerUid
	 * @return
	 */
	public List<Account> getAccountsOfCustomer(String customerUid) {
		Customer customer = customerDao.getCustomer(customerUid);
		if(null == customer) {
			throw new IllegalArgumentException("The customer does not exist.");
		}
		return getAccountsOfCustomer(customer);
	}

	/**
	 * Returns all accounts of the given customer.
	 * 
	 * @param customer
	 * @return
	 */
	public List<Account> getAccountsOfCustomer(Customer customer) {
		return accountDao.getAccountsOfCustomer(customer.getUid());
	}

	/**
	 * Creates a new customer.
	 * 
	 * @param customer
	 */
	public void createCustomer(Customer customer) {
		// Check wether a customer with the same uid already exists
		Customer c = customerDao.getCustomer(customer.getUid());
		if (null != c) {
			throw new IllegalArgumentException("customer already exists.");
		}

		try {
			// Validate the customer
			validateCustomer(customer);

			// Create the asset
			customerDao.createCustomer(customer);
		} catch(ValidationException e) {
		}
	}

	/**
	 * Updates an existing customer.
	 * 
	 * @param uid
	 * @param customer
	 */
	public void updateCustomer(String uid, Customer customer) {
		// Lookup the customer
		Customer c = customerDao.getCustomer(uid);
		if (null == c) {
			throw new IllegalArgumentException("The customer does not exist.");
		}

		try {
			// Validate the customer
			validateCustomer(customer);

			// Bind data
			c.setName(customer.getName());

			// @todo Implement security
			customerDao.updateCustomer(c);
		} catch(ValidationException e) {
		}
	}

	/**
	 * Deletes an existing customer
	 * 
	 * @param customer
	 */
	public void deleteCustomer(Customer customer) {
		customerDao.deleteCustomer(customer);
	}

	protected void validateCustomer(Customer customer) {
		// Create a validatable with the customer as payload
		Validateable<Customer> validateable = Validateable.create(customer);

		// Instantiate a new validator
		CustomerValidator validator = new CustomerValidator();

		// Validate the validateable and report errors, if any
		validator.validate(validateable);
		if (!validateable.isValid()) {
			throw new ValidationException(validateable.getErrors());
		}
	}

}
