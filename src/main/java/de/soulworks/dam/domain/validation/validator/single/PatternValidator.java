package de.soulworks.dam.domain.validation.validator.single;

import de.soulworks.dam.domain.validation.Validateable;
import de.soulworks.dam.domain.validation.ValidationError;
import de.soulworks.dam.domain.validation.Validator;

import java.util.regex.Pattern;

/**
 * @author Christian Schmitz <csc@soulworks.de
 */
public class PatternValidator implements Validator<String> {
	/**
	 * The regexp pattern.
	 */
	private Pattern pattern = null;

	/**
	 * Constructor.
	 *
	 * @param pattern Regular expression pattern
	 */
	public PatternValidator(String pattern) {
		this(Pattern.compile(pattern));
	}

	/**
	 * Constructor.
	 *
	 * @param pattern Java regex pattern
	 */
	public PatternValidator(Pattern pattern) {
		this.pattern = pattern;
	}

	/**
	 * Constructor.
	 *
	 * @param pattern Regular expression pattern
	 * @param flags   Compile flags for java.util.regex.Pattern
	 */
	public PatternValidator(String pattern, final int flags) {
		this(Pattern.compile(pattern, flags));
	}

	@Override
	public void validate(Validateable<String> validatable) {
		String value = validatable.getValue();

		if (!pattern.matcher(value).matches()) {
			validatable.error(new ValidationError("Pattern didn't match."));
		}
	}
}
