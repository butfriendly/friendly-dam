package de.soulworks.dam.webservice.service;

import de.soulworks.dam.domain.Account;
import de.soulworks.dam.webservice.ServiceTest;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Christian Schmitz <csc@soulworks.de
 */
@Component
public class AccountServiceTest extends ServiceTest {
	private static final Logger log = LoggerFactory.getLogger(AccountServiceTest.class);

	@Autowired
	private AccountService accountService;

	@Test
	public void testCreateAccount() {
		Account account = Account.create()
				.setCustomerUid("customer1")
				.setUsername("jondoe1")
				.setPasswordHash("hash");

		accountService.createAccount(account);

		Assert.assertNotNull(account.getId());
		Assert.assertNotNull(account.getRevision());

		Assert.assertEquals("jondoe1", account.getUsername());
		Assert.assertEquals("customer1", account.getCustomerUid());

		accountService.deleteAccount(account);
	}

	@Test
	public void testDeleteAccount() {
		Account account = Account.create()
				.setCustomerUid("customer1")
				.setUsername("jondoe1")
				.setPasswordHash("hash");

		accountService.createAccount(account);

		Assert.assertNotNull(account.getId());
		Assert.assertNotNull(account.getRevision());

		accountService.deleteAccount(account);

		Account a = accountService.getAccount("jondoe1");

		Assert.assertNull(a);
	}

	@Test
	public void testGetAccount() {
		Account account = Account.create()
				.setCustomerUid("customer1")
				.setUsername("jondoe2")
				.setPasswordHash("hash");

		accountService.createAccount(account);

		Assert.assertNotNull(account.getId());
		Assert.assertNotNull(account.getRevision());

		Account a = accountService.getAccount("jondoe2");

		Assert.assertNotNull(a);
		
		Assert.assertEquals(account.getUsername(), a.getUsername());
		Assert.assertEquals(account.getCustomerUid(), a.getCustomerUid());
		Assert.assertEquals(account.getPasswordHash(), a.getPasswordHash());

		accountService.deleteAccount(account);
	}
	
	@Test
	public void testGetAccounts() {
		accountService.getAccounts();
	}

	@Test
	public void testUpdateAccount() {
		Account account = Account.create()
				.setCustomerUid("customer1")
				.setUsername("jondoe3")
				.setPasswordHash("hash");

		accountService.createAccount(account);

		Assert.assertNotNull(account.getId());
		Assert.assertNotNull(account.getRevision());

		Account a = accountService.getAccount("jondoe3");

		Assert.assertNotNull(a);
		Assert.assertEquals(account.getId(), a.getId());
		Assert.assertEquals(account.getRevision(), a.getRevision());

		// We must not change the username because it acts as uid
		a.setPasswordHash("newhash").setCustomerUid("customer2");

		log.info("Revision before: {}", a.getRevision());
		accountService.updateAccount(a.getUsername(), a);
		log.info("Revision afterwards: {}", a.getRevision());

		Assert.assertEquals(account.getId(), a.getId());
		Assert.assertNotSame(account.getRevision(), a.getRevision());
		Assert.assertEquals("customer2", a.getCustomerUid());
		Assert.assertEquals("newhash", a.getPasswordHash());

		// Couch db doesn't currently update the revision of the documents after
		// an update, so we can't delete it.
		// @see http://code.google.com/p/ektorp/issues/detail?id=45
//		accountService.deleteAccount(a);
	}

}
