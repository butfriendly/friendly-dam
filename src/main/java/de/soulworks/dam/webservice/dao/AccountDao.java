package de.soulworks.dam.webservice.dao;

import de.soulworks.dam.domain.Account;

import java.util.List;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
public interface AccountDao {
	public Account getAccount(String username);

	public List<Account> getAccounts();

	public List<Account> getAccountsOfCustomer(String customerUid);

	public void createAccount(Account account);

	public void updateAccount(Account account);

	public void deleteAccount(Account account);
}
