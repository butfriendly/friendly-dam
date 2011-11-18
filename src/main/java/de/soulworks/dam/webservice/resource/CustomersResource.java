package de.soulworks.dam.webservice.resource;

import de.soulworks.dam.domain.Customer;
import de.soulworks.dam.webservice.service.CustomerService;
import com.sun.jersey.api.JResponse;
import com.sun.jersey.api.core.ResourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import java.util.List;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@Path("customers")
@Controller
@Scope("singleton")
public class CustomersResource {
	@Autowired
	private CustomerService customerService;

	@POST
	@Consumes({MediaType.APPLICATION_XML})
	public void createCustomer(JAXBElement<Customer> element) {
		Customer customer = element.getValue();
		customerService.createCustomer(customer);
	}

	@GET
	@Produces({MediaType.APPLICATION_XML})
	public JResponse<List<Customer>> getCustomers() {
		// Wrap it!
		return JResponse.ok(customerService.getCustomers()).build();
	}

	@Path("{customerId}")
	public CustomerResource getCustomerResource(@Context ResourceContext resourceContext, @PathParam("customerId") String id) {
		// Retrieve the customer from the datastore
		Customer customer = customerService.getCustomer(id);

		// Did we get a customer
		if(null == customer) {
			throw new WebApplicationException(Response
					.status(Response.Status.NOT_FOUND)
					.entity("The requested customer could not be found: " + id)
					.build());
		}

		// Create a fresh resource for the given customer
		CustomerResource resource = resourceContext.getResource(CustomerResource.class);
		resource.setCustomer(customer);
		return resource;
	}
}
