package de.soulworks.dam.webservice.service;

import de.soulworks.dam.domain.Account;
import de.soulworks.dam.domain.validation.Validateable;
import de.soulworks.dam.domain.validation.ValidationException;
import de.soulworks.dam.domain.validation.validator.compound.AccountValidator;
import de.soulworks.dam.webservice.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@Service
public class AccountService {
	@Autowired
	@Qualifier("accountDao")
	private AccountDao accountDao;

	public Account getAccount(String username) {
		return accountDao.getAccount(username);
	}

	public List<Account> getAccounts() {
		return accountDao.getAccounts();
	}

	public void createAccount(Account account) {
		try {
			// Validate the account
			validateAccount(account);

			accountDao.createAccount(account);
		} catch(ValidationException e) {
		}
	}

	public void updateAccount(String username, Account account) {
		Account a = getAccount(username);
		if(null == a) {
			throw new IllegalArgumentException("The account "+ username +" could not be found.");
		}

		try {
			// Validate the account
			validateAccount(account);

			// Bind data
			a.setCustomerUid(account.getCustomerUid());
			a.setPasswordHash(account.getPasswordHash());

			// The username acts as identifier and must not be updated
//			a.setUsername(account.getUsername());

			// Update account at the datastore
			accountDao.updateAccount(a);
		} catch(ValidationException e) {
		}
	}

	public void deleteAccount(Account account) {
		accountDao.deleteAccount(account);
	}

	protected void validateAccount(Account account) {
		// Create a validatable
		Validateable<Account> accountValidateable = Validateable.create(account);

		// Create a validator and validate the validatable
		AccountValidator validator = new AccountValidator();
		validator.validate(accountValidateable);

		// Throw exception if the account is invalid
		if(!accountValidateable.isValid()) {
			throw new ValidationException(accountValidateable.getErrors());
		}
	}
}
