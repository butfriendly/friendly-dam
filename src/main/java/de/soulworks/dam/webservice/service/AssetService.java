package de.soulworks.dam.webservice.service;

import de.soulworks.dam.domain.Asset;
import de.soulworks.dam.domain.validation.Validateable;
import de.soulworks.dam.domain.validation.ValidationException;
import de.soulworks.dam.domain.validation.validator.compound.AssetValidator;
import de.soulworks.dam.webservice.dao.AssetDao;

import java.util.List;

import de.soulworks.dam.domain.Bucket;
import de.soulworks.dam.webservice.dao.BucketDao;
import de.soulworks.dam.webservice.dao.CustomerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@Service
public class AssetService {
	private static final Logger log = LoggerFactory.getLogger(AssetService.class);
	
	@Autowired
	@Qualifier("assetDao")
	private AssetDao assetDao;

	@Autowired
	@Qualifier("bucketDao")
	private BucketDao bucketDao;

	@Autowired
	@Qualifier("customerDao")
	private CustomerDao customerDao;

	public Asset getAsset(String id) {
		return assetDao.getAsset(id);
	}

	public List<Asset> getAssets() {
		return assetDao.getAssets();
	}

	public List<Asset> getAssetsOfBucket(String bucketUid) {
		Bucket bucket = bucketDao.getBucket(bucketUid);

		if(null == bucket) {
			throw new IllegalArgumentException("Invalid bucket");
		}

		return getAssetsOfBucket(bucket);
	}

	public List<Asset> getAssetsOfBucket(Bucket bucket) {
		return assetDao.getAssetsOfBucket(bucket.getUid());
	}

	public void createAsset(Asset asset) {
		// Check wether an asset with the same uid already exists
		Asset a = assetDao.getAsset(asset.getUid());
		if (null != a) {
			throw new IllegalArgumentException("An asset with that uid "+a.getUid()+" ("+a.getId()+") already exists.");
		}

		try {
			// Validate the asset
			validateAsset(asset);

			// Create the asset
			assetDao.createAsset(asset);
		} catch(ValidationException e) {
		}
	}

	public void updateAsset(String uid, Asset asset) {
		// Retrieve asset with the given uid
		Asset a = assetDao.getAsset(uid);

		if(null == a) {
			throw new IllegalArgumentException("There is no asset with the given uid.");
		}

		try {
			// Validate the asset
			validateAsset(asset);

			// Bind data
			a.setTitles(asset.getTitles());
			a.setDescriptions(asset.getDescriptions());

			// Update the asset at the storage
			assetDao.updateAsset(a);
		} catch(ValidationException e) {
		}
	}

	public void deleteAsset(Asset asset) {
		assetDao.deleteAsset(asset);
	}

	protected void validateAsset(Asset asset) {
		Validateable<Asset> validateable = Validateable.create(asset);
		AssetValidator validator = new AssetValidator();
		validator.validate(validateable);
		if (!validateable.isValid()) {
			throw new ValidationException(validateable.getErrors());
		}
	}
}
