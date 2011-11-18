package de.soulworks.dam.domain.validation;

import java.util.List;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
public class ValidationException extends RuntimeException {
	/**
	 * Construct.
	 */
	public ValidationException() {
		super();
	}

	public ValidationException(List<ValidationError> errors) {
		
	}

	/**
	 * Construct.
	 *
	 * @param message the message
	 */
	public ValidationException(String message) {
		super(message);
	}

	/**
	 * Construct.
	 *
	 * @param cause the cause
	 */
	public ValidationException(Throwable cause) {
		super(cause);
	}

	/**
	 * Construct.
	 *
	 * @param message the message
	 * @param cause   the cause
	 */
	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}
}
