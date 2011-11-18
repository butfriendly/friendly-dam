package de.soulworks.dam.webservice.service;

import de.soulworks.dam.domain.Asset;
import de.soulworks.dam.domain.validation.validator.compound.BucketValidator;
import de.soulworks.dam.domain.validation.Validateable;
import de.soulworks.dam.domain.validation.ValidationException;
import de.soulworks.dam.domain.Bucket;
import de.soulworks.dam.webservice.dao.BucketDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@Service
public class BucketService {
	@Autowired
	@Qualifier("bucketDao")
	private BucketDao bucketDao;

	@Autowired
	private AssetService assetService;

	/**
	 * Retrieve a single bucket
	 * 
	 * @param id
	 * @return
	 */
	public Bucket getBucket(String id) {
		return bucketDao.getBucket(id);
	}

	/**
	 * Returns all available buckets
	 * @return
	 */
	public List<Bucket> getBuckets() {
		return bucketDao.getBuckets();
	}

	/**
	 * Imports an existing asset from onw bucket to another.
	 * 
	 * @param asset
	 * @param bucket
	 * @return
	 */
	public Asset importAssetToBucket(Asset asset, Bucket bucket) {
		// @todo Check constraints

		// Create a duplicate of the asset
		Asset newAsset = asset.clone();

		// Put the newly created asset at the bucket
		newAsset.setBucketUid(bucket.getUid());

		// @todo Compare and update profiles
		
		// Store the new asset
		assetService.createAsset(newAsset);

		return newAsset;
	}

	/**
	 * Creates a new bucket
	 * 
	 * @param bucket
	 */
	public void createBucket(Bucket bucket) {
		// @todo Implement security

		// Check wether a bucket with the same uid already exists
		Bucket b = bucketDao.getBucket(bucket.getUid());
		if (null != b) {
			throw new IllegalArgumentException("A bucket with that uid already exists.");
		}

		try {
			// Validate the bucket
			validateBucket(bucket);
			
			// Create the bucket
			bucketDao.createBucket(bucket);
		} catch(ValidationException e) {
		}
	}

	/**
	 * Updates the bucket referenced by {@code uid} with data from {@code bucket}
	 * 
	 * @param uid
	 * @param bucket
	 */
	public void updateBucket(String uid, Bucket bucket) {
		// Lookup the bucket
		Bucket b = bucketDao.getBucket(uid);

		// Check wether we got a bucket or not
		if (null == b) {
			throw new IllegalArgumentException("The bucket "+ uid +"does not exist.");
		}

		try {
			// Validate the bucket
			validateBucket(bucket);

			// Bind data
			b.setNames(bucket.getNames());
			b.setDescriptions(bucket.getDescriptions());
			b.setConstraints(bucket.getConstraints());
			b.setProfiles(bucket.getProfiles());

			// Update the bucket
			bucketDao.updateBucket(b);
		} catch(ValidationException e) {
		}
	}

	/**
	 * Deletes the given {@code bucket}
	 * @param bucket
	 */
	public void deleteBucket(Bucket bucket) {
		bucketDao.deleteBucket(bucket);
	}

	/**
	 * Validates the given {@code bucket}
	 * @param bucket
	 */
	protected void validateBucket(Bucket bucket) {
		// Create a validatable
		Validateable<Bucket> bucketValidateable = Validateable.create(bucket);

		// Create a validator and validate the validatable
		BucketValidator validator = new BucketValidator();
		validator.validate(bucketValidateable);

		// Throw exception if bucket is invalid
		if(!bucketValidateable.isValid()) {
			throw new ValidationException(bucketValidateable.getErrors());
		}
	}
}
