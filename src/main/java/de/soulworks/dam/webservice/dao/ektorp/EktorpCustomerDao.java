package de.soulworks.dam.webservice.dao.ektorp;

import de.soulworks.dam.domain.Customer;
import de.soulworks.dam.webservice.dao.CustomerDao;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Christian Schmitz <csc@soulworks.de
 */
@Component
@View( name="all", map = "function(doc) { if ('Customer' == doc.documentType) { emit(doc.uid, doc._id) } }")
public class EktorpCustomerDao extends CouchDbRepositorySupport<Customer>  implements CustomerDao{
	private static final Logger log = LoggerFactory.getLogger(EktorpCustomerDao.class);

	@Autowired
	public EktorpCustomerDao(@Qualifier("ektorpDatabase") CouchDbConnector db) {
		super(Customer.class, db);

		// Trigger automatic view generation
		initStandardDesignDocument();
	}

	public List<Customer> findAll() {
		ViewQuery q = createQuery("all").includeDocs(true);
		return db.queryView(q, Customer.class);
	}

	private List<Customer> findByUid(String uid) {
		ViewQuery q = createQuery("all").includeDocs(true).key(uid);
		return db.queryView(q, Customer.class);
	}

	@Override
	public Customer getCustomer(String customerUid) {
		return findByUid(customerUid).get(0);
	}

	@Override
	public List<Customer> getCustomers() {
		return findAll();
	}

	@Override
	public void createCustomer(Customer customer) {
		db.create(customer);
	}

	@Override
	public void updateCustomer(Customer customer) {
		db.update(customer);
	}

	@Override
	public void deleteCustomer(Customer customer) {
		db.delete(customer.getId(), customer.getRevision());
	}
}
