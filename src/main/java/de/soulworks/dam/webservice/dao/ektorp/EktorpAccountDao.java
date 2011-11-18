package de.soulworks.dam.webservice.dao.ektorp;

import de.soulworks.dam.domain.Account;
import de.soulworks.dam.webservice.dao.AccountDao;
import org.ektorp.CouchDbConnector;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.ViewQuery;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.GenerateView;
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
@View( name="all", map = "function(doc) { if ('Account' == doc.documentType) { emit(doc.username, doc._id) } }")
public class EktorpAccountDao  extends CouchDbRepositorySupport<Account> implements AccountDao {
	private static final Logger log = LoggerFactory.getLogger(EktorpAccountDao.class);

	@Autowired
	public EktorpAccountDao(@Qualifier("ektorpDatabase") CouchDbConnector db) {
		super(Account.class, db);

		// Trigger automatic view generation
		initStandardDesignDocument();
	}

	private List<Account> findByUsername(String username) {
		ViewQuery q = createQuery("all").includeDocs(true).key(username);
		return db.queryView(q, Account.class);
	}

	@GenerateView
	private List<Account> findByCustomerUid(String customerUid) {
		return queryView("by_customerUid", customerUid);
	}

	@Override
	public Account getAccount(String username) {
		Account account = null;
		try {
			List<Account> accounts = findByUsername(username);
			if (accounts.size() == 1) {
				account = accounts.get(0);
			} else if (accounts.size() > 1) {
				log.info("We found {} accounts when looking for {}", accounts.size(), username);
				throw new IllegalStateException("We found more than 1 matching account for "+username+".");
			}
		} catch(DocumentNotFoundException e) {
			log.info("The account {} does not exist.", username);
		}
		return account;
	}

	@Override
	public List<Account> getAccounts() {
		ViewQuery q = createQuery("all").includeDocs(true);
		return db.queryView(q, Account.class);
	}

	@Override
	public List<Account> getAccountsOfCustomer(String customerUid) {
		return findByCustomerUid(customerUid);
	}

	@Override
	public void createAccount(Account account) {
		db.create(account);
	}

	@Override
	public void updateAccount(Account account) {
		db.update(account);
	}

	@Override
	public void deleteAccount(Account account) {
		db.delete(account.getId(), account.getRevision());
	}
}
