package de.soulworks.dam.domain.validation.validator.compound;

import de.soulworks.dam.domain.Account;
import de.soulworks.dam.domain.validation.Validateable;
import de.soulworks.dam.domain.validation.ValidationError;
import de.soulworks.dam.domain.validation.Validator;
import de.soulworks.dam.domain.validation.validator.single.PatternValidator;
import de.soulworks.dam.webservice.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.regex.Pattern;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@Component
public class AccountValidator implements Validator<Account> {
	public static final Pattern PATTERN_USERNAME = Pattern.compile("^[\\w\\d]{3,15}$");

	@Autowired
	@Qualifier("accountDao")
	private AccountDao accountDao;

	@Override
	public void validate(Validateable<Account> validatable) {
		Account account = validatable.getValue();

		// Get a pattern-validator 
		PatternValidator usernamePatternValidator = new PatternValidator(PATTERN_USERNAME);

		// Validate username
		Validateable<String> usernameValidatable = Validateable.create(account.getUsername());
		usernamePatternValidator.validate(usernameValidatable);
		if (!usernameValidatable.isValid()) {
			validatable.error(new ValidationError("Invalid username"));
		}
/*
		Account a = accountDao.getAccount(account.getUsername());
		if (null != a) {
			validatable.error(new ValidationError("An account with that username already exists."));
		}
*/
	}
}
