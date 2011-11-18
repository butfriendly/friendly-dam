package de.soulworks.dam.domain.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A validatable allows to utilize {@link Validator}s for validation.
 * 
 * @author Christian Schmitz <csc@soulworks.de>
 */
public class Validateable<T> {
	/**
	 * the value object
	 */
	private T value;

	/**
	 * the list of errors
	 */
	private ArrayList<ValidationError> errors;

	/**
	 * Constructor.
	 */
	public Validateable() {
	}

	/**
	 * Constructor.
	 *
	 * @param value The value that will be tested
	 */
	public Validateable(T value) {
		this.value = value;
	}

	/**
	 * Validatable factory
	 * 
	 * @param value
	 * @param <T>
	 * @return
	 */
	public static <T> Validateable<T> create(T value) {
		return new Validateable<T>(value);
	}

	/**
	 * Sets the value object that will be returned by {@link #getValue()}.
	 *
	 * @param value the value object
	 */
	public void setValue(T value) {
		this.value = value;
	}

	/**
	 * Retrieves the value to be validated.
	 *
	 * @return the value to be validated
	 */
	public T getValue() {
		return value;
	}

	/**
	 * Reports an error against this <code>IValidatable</code>'s value. Multiple errors can be
	 * reported by calling this method multiple times.
	 *
	 * @param error an <code>IValidationError</code> to be reported
	 */
	public void error(ValidationError error) {
		if (null == errors) {
			errors = new ArrayList<ValidationError>();
		}
		errors.add(error);
	}

	/**
	 * Retrieves an unmodifiable list of any errors reported against this <code>IValidatable</code>
	 * instance.
	 *
	 * @return an unmodifiable list of errors
	 */
	public List<ValidationError> getErrors() {
		if (errors == null) {
			return Collections.emptyList();
		} else {
			return Collections.unmodifiableList(errors);
		}
	}

	/**
	 * Queries the current state of this <code>IValidatable</code> instance.
	 * <code>IValidatable</code>s should assume they are valid until
	 * {@link #error(ValidationError)} is called.
	 *
	 * @return <code>true</code> if the object is in a valid state, <code>false</code> if otherwise
	 */
	public boolean isValid() {
		return null == errors;
	}
}
