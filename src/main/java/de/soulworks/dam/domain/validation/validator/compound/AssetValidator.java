package de.soulworks.dam.domain.validation.validator.compound;

import de.soulworks.dam.domain.Asset;
import de.soulworks.dam.domain.validation.Validateable;
import de.soulworks.dam.domain.validation.ValidationError;
import de.soulworks.dam.domain.validation.Validator;
import de.soulworks.dam.domain.validation.validator.single.MapLengthValidator;
import de.soulworks.dam.domain.validation.validator.single.PatternValidator;
import de.soulworks.dam.webservice.dao.BucketDao;
import de.soulworks.dam.webservice.dao.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@Component
public class AssetValidator implements Validator<Asset> {
	public static final Pattern PATTERN_UID = Pattern.compile("^[\\w\\.]+$");

	@Autowired
	@Qualifier("bucketDao")
	private BucketDao bucketDao;

	@Autowired
	@Qualifier("customerDao")
	private CustomerDao customerDao;

	@Override
	public void validate(Validateable<Asset> assetValidatable) {
		Asset asset = assetValidatable.getValue();

		PatternValidator uidPatternValidator  = new PatternValidator(PATTERN_UID);
		MapLengthValidator mapLengthValidator = new MapLengthValidator(true, 1);

		// Check for a valid uid
		Validateable<String> uidValidatable = Validateable.create(asset.getUid());
		uidPatternValidator.validate(uidValidatable);
		if (uidValidatable.isValid()) {
			assetValidatable.error(new ValidationError("Invalid uid"));
		}

		// There has to be at least one description
		Validateable<Map> descriptionValidateable = Validateable.create((Map)asset.getDescriptions());
		mapLengthValidator.validate(descriptionValidateable);
		if (!descriptionValidateable.isValid()) {
			assetValidatable.error(new ValidationError("No descriptions found"));
		}

		// There has to be at least one title
		Validateable<Map> titleValidateable = Validateable.create((Map)asset.getTitles());
		mapLengthValidator.validate(titleValidateable);
		if (!titleValidateable.isValid()) {
			assetValidatable.error(new ValidationError("No titles found"));
		}

		// Every asset needs to have an type
		if(null == asset.getType()) {
			assetValidatable.error(new ValidationError("No type given"));
		}

		// Check for a valid bucket
		// @todo: NPE at tests because of spring injection does not populate bucketDao
		/*
		String bucketUid = asset.getBucketUid();
		Bucket bucket = bucketDao.getBucket(bucketUid);
		if (null == bucket || bucket.isNew() || bucket.isDeleted()) {
			assetValidatable.error(new ValidationError("The bucket does not exist"));
		}*/
/*
		// Check for valid originator
		Customer customer = customerDao.getCustomer(asset.getOriginatorUid());
		if (null == customer || customer.isNew() || customer.isDeleted()) {
			assetValidatable.error(new ValidationError("The customer does not exist"));
		}
*/		
	}

}
