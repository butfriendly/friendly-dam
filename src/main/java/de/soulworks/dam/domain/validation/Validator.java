package de.soulworks.dam.domain.validation;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
public interface Validator<T> {
	public void validate(Validateable<T> validatable);
}
