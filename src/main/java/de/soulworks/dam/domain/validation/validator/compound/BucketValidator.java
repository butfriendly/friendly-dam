package de.soulworks.dam.domain.validation.validator.compound;

import de.soulworks.dam.domain.Bucket;
import de.soulworks.dam.domain.validation.Validateable;
import de.soulworks.dam.domain.validation.ValidationError;
import de.soulworks.dam.domain.validation.Validator;
import de.soulworks.dam.domain.validation.validator.single.MapLengthValidator;
import de.soulworks.dam.domain.validation.validator.single.PatternValidator;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
public class BucketValidator implements Validator<Bucket> {
	public static final Pattern PATTERN_UID = Pattern.compile("^[\\w\\d\\.\\-]+$");
	
	public void validate(Validateable<Bucket> bucketValidatable) {
		Bucket bucket = bucketValidatable.getValue();

		PatternValidator uidPatternValidator  = new PatternValidator(PATTERN_UID);
		MapLengthValidator mapLengthValidator = new MapLengthValidator(true, 1);

		// Validate uid
		Validateable<String> uidValidatable = Validateable.create(bucket.getUid());
		uidPatternValidator.validate(uidValidatable);
		if (!uidValidatable.isValid()) {
			bucketValidatable.error(new ValidationError("Invalid uid"));
		}

		// There has to be at least one description
		Validateable<Map> descriptionValidateable = Validateable.create((Map)bucket.getDescriptions());
		mapLengthValidator.validate(descriptionValidateable);
		if (!descriptionValidateable.isValid()) {
			bucketValidatable.error(new ValidationError("No descriptions found"));
		}

		// There has to be at least one description
		Validateable<Map> nameValidateable = Validateable.create((Map)bucket.getNames());
		mapLengthValidator.validate(nameValidateable);
		if (!nameValidateable.isValid()) {
			bucketValidatable.error(new ValidationError("No names found"));
		}
	}
}
