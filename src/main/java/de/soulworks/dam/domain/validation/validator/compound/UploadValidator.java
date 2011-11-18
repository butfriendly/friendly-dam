package de.soulworks.dam.domain.validation.validator.compound;

import de.soulworks.dam.domain.Upload;
import de.soulworks.dam.domain.validation.Validateable;
import de.soulworks.dam.domain.validation.ValidationError;
import de.soulworks.dam.domain.validation.Validator;
import de.soulworks.dam.domain.validation.validator.single.PatternValidator;
import de.soulworks.dam.webservice.dao.BucketDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.regex.Pattern;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
public class UploadValidator implements Validator<Upload> {
	public static final Pattern PATTERN_UID = Pattern.compile("^[\\w\\d\\-\\.]+$");

	@Autowired
	@Qualifier("bucketDao")
	private BucketDao bucketDao;

	@Override
	public void validate(Validateable<Upload> uploadValidatable) {
		Upload upload = uploadValidatable.getValue();

		PatternValidator uidPatternValidator = new PatternValidator(PATTERN_UID);

		if (null == upload.getStatus()) {
			uploadValidatable.error(new ValidationError("No status given"));
		}

		// Validate the uid
		Validateable<String> uidValidatable = Validateable.create(upload.getUid());
		uidPatternValidator.validate(uidValidatable);
		if (!uidValidatable.isValid()) {
			uploadValidatable.error(new ValidationError("Invalid uid"));
		}
		
		// Check for a valid bucket
		/*
		Bucket bucket = bucketDao.getBucket(upload.getBucketUid());
		if (null == bucket || bucket.isNew() || bucket.isDeleted()) {
			uploadValidatable.error(new ValidationError("The referenced bucket does not exist"));
		}
		*/
	}
}
