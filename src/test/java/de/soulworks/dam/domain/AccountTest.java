package de.soulworks.dam.domain;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
public class AccountTest {

	@Test
	public void testCreate() {
		Account account = Account.create();
		Assert.assertEquals("Account", account.getClass().getSimpleName());
	}

	@Test
	public void testConstruct() {
		Account account = new Account();
		Assert.assertEquals("Account", account.getClass().getSimpleName());
	}

	@Test
	public void testSetAndGetIsLocked() {
		Account account = Account.create().setLocked(true);
		Assert.assertTrue(account.isLocked());
	}

	@Test
	public void testSetAndGetIsExpired() {
		Account account = Account.create().setExpired(true);
		Assert.assertTrue(account.isExpired());
	}

	@Test
	public void testSetAndGetUsername() {
		Account account = Account.create().setUsername("username");
		Assert.assertEquals("username", account.getUsername());
	}

	@Test
	public void testSetAndGetCustomerUid() {
		Account account = Account.create().setCustomerUid("customer");
		Assert.assertEquals("customer", account.getCustomerUid());
	}

}
