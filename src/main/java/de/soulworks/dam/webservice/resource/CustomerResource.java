package de.soulworks.dam.webservice.resource;

import de.soulworks.dam.domain.Account;
import de.soulworks.dam.domain.Customer;
import de.soulworks.dam.webservice.service.AccountService;
import de.soulworks.dam.webservice.service.CustomerService;
import com.sun.jersey.api.JResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;
import java.net.URI;
import java.util.List;

/**
 * @author Christian Schmitz <csc@soulworks.de
 */
@Controller
@Scope("prototype")
public class CustomerResource {
	private Customer customer;

	@Context
	private UriInfo uriInfo;

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AccountService accountService;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@PUT
	@Consumes({ MediaType.APPLICATION_XML })
	public void updateCustomer(JAXBElement<Customer> element) {
		Customer customer = element.getValue();
		customerService.updateCustomer(getCustomer().getUid(), customer);
	}

	@DELETE
	public void deleteCustomer(JAXBElement<Customer> element) {
		customerService.deleteCustomer(customer);
	}

	@GET
	@Produces({MediaType.APPLICATION_XML})
	public JResponse<Customer> getCustomerDetails() {
		return JResponse.ok(getCustomer()).build();
	}

	@GET
	@Path("accounts")
	@Produces({MediaType.APPLICATION_XML})
	public JResponse<List<Account>> getAccounts() {
		return JResponse.ok(customerService.getAccountsOfCustomer(getCustomer())).build();
	}

	@POST
	@Path("accounts")
	public Response createAccount(JAXBElement<Account> element) {
		Account account = element.getValue();

		// Enforce customer of the resource-path
		account.setCustomerUid(getCustomer().getUid());

		// Create the account
		accountService.createAccount(account);

		// Generate URI for the new resource
		URI uri = uriInfo.getAbsolutePathBuilder().path(account.getId()).build();

		return Response.created(uri).build();
	}
}
