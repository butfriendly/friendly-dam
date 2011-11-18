package de.soulworks.dam.domain.validation;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
public class ValidationError {
	private static final long serialVersionUID = 1L;

	/** default message used when all keys yield no message */
	private String message;
	
	public ValidationError() {}
	
	public ValidationError(String message) {
		this.message = message;
	}

	/**
	 * Retrieves the error message (usually user-facing).
	 */
	public String getErrorMessage() {
		return message;
	}
}
