package de.soulworks.dam.domain.validation.validator.single;

import de.soulworks.dam.domain.validation.Validateable;
import de.soulworks.dam.domain.validation.ValidationError;
import de.soulworks.dam.domain.validation.Validator;

import java.util.Map;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
public class MapLengthValidator implements Validator<Map> {
    /** True if minimum bound should be checked. */
    private  boolean checkMin;

    /** True if maximum bound should be checked. */
    private  boolean checkMax;

	/**
	 * Lower bound on valid decimal number.
	 */
	private int min;

	/**
	 * Upper bound on valid decimal number.
	 */
	private int max;

	/**
	 * Constructor
	 *
	 * @param checkMin
	 * @param min
	 */
	public MapLengthValidator(boolean checkMin, int min) {
		this.checkMin = checkMin;
		this.min = min;
	}

	/**
	 * Constructor
	 *
	 * @param checkMin
	 * @param min
	 * @param checkMax
	 * @param max
	 */
	public MapLengthValidator(boolean checkMin, int min, boolean checkMax, int max) {
		this(checkMin, min);
		this.checkMax = checkMax;
		this.max = max;
	}

	@Override
	public void validate(Validateable<Map> validatable) {
		int size = validatable.getValue().size();

		if ((checkMin && size < min) || (checkMax && size > max)) {
			validatable.error(new ValidationError("Invalid size"));
		}
	}
}
