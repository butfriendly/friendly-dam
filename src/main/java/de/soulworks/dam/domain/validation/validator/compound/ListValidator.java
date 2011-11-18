package de.soulworks.dam.domain.validation.validator.compound;

import de.soulworks.dam.domain.List;
import de.soulworks.dam.domain.validation.Validateable;
import de.soulworks.dam.domain.validation.ValidationError;
import de.soulworks.dam.domain.validation.Validator;
import de.soulworks.dam.domain.validation.validator.single.MapLengthValidator;
import de.soulworks.dam.domain.validation.validator.single.PatternValidator;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
public class ListValidator implements Validator<List> {
	public static final Pattern PATTERN_UID = Pattern.compile("^[\\w\\d\\-\\.]+$");

	@Override
	public void validate(Validateable<List> validatable) {
		List list = validatable.getValue();

		// Prepare the validators
		PatternValidator uidPatternValidator  = new PatternValidator(PATTERN_UID);
		MapLengthValidator mapLengthValidator = new MapLengthValidator(true, 1);

		// Validate the uid
		Validateable<String> uidValidatable = Validateable.create(list.getUid());
		uidPatternValidator.validate(uidValidatable);
		if (!uidValidatable.isValid()) {
			validatable.error(new ValidationError("Invalid uid"));
		}

		// There has to be at least one title
		Validateable<Map> nameValidateable = Validateable.create((Map)list.getNames());
		mapLengthValidator.validate(nameValidateable);
		if (!nameValidateable.isValid()) {
			validatable.error(new ValidationError("No titles found"));
		}

		// There has to be at least one description
		Validateable<Map> descriptionValidateable = Validateable.create((Map)list.getDescriptions());
		mapLengthValidator.validate(descriptionValidateable);
		if (!descriptionValidateable.isValid()) {
			validatable.error(new ValidationError("No descriptions found"));
		}
	}
}
