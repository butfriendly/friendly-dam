package de.soulworks.dam.domain.validation.validator.single;

import de.soulworks.dam.domain.validation.Validateable;
import de.soulworks.dam.domain.validation.ValidationError;
import de.soulworks.dam.domain.validation.Validator;

/**
 * @author Christian Schmitz <csc@soulworks.de
 */
public class UidValidator implements Validator<String> {
	@Override
	public void validate(Validateable<String> validatable) {
		validatable.error(new ValidationError());
	}
}
