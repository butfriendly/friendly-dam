package de.soulworks.dam.domain.validation.validator.compound;

import de.soulworks.dam.domain.Customer;
import de.soulworks.dam.domain.validation.Validateable;
import de.soulworks.dam.domain.validation.ValidationError;
import de.soulworks.dam.domain.validation.Validator;
import de.soulworks.dam.domain.validation.validator.single.PatternValidator;

import java.util.regex.Pattern;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
public class CustomerValidator implements Validator<Customer> {
	public static final Pattern PATTERN_UID = Pattern.compile("^[\\w\\.]+$");

	public void validate(Validateable<Customer> customerValidatable) {
		Customer customer = customerValidatable.getValue();
		
		PatternValidator uidPatternValidator = new PatternValidator(PATTERN_UID);

		Validateable<String> uidValidatable = Validateable.create(customer.getUid());
		uidPatternValidator.validate(uidValidatable);
		if (!uidValidatable.isValid()) {
			customerValidatable.error(new ValidationError("Invalid uid"));
		}
	}
}
