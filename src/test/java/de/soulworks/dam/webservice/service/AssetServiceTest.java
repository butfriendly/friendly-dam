package de.soulworks.dam.webservice.service;

import de.soulworks.dam.domain.Asset;
import de.soulworks.dam.domain.Bucket;
import de.soulworks.dam.webservice.ServiceTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;
import java.util.UUID;

/**
 * @author Christian Schmitz <csc@soulworks.de
 */
public class AssetServiceTest extends ServiceTest {
	@Autowired
	private BucketService bucketService;

	@Autowired
	private AssetService assetService;

	@Test
	public void testCreateAsset() {
		String uid = this.getClass().getSimpleName() +"."+ UUID.randomUUID().toString();

		Bucket bucket = Bucket.create().setUid(uid+"1").setName("name", Locale.ENGLISH).setDescription("Desc", Locale.ENGLISH);
		bucketService.createBucket(bucket);

		Assert.assertNotNull(bucket.getId());
		Assert.assertNotNull(bucket.getRevision());

		Asset asset = Asset.create().setUid(uid+"2").setTitle("Title", Locale.ENGLISH).setDescription("Desc", Locale.ENGLISH).setBucket(bucket);
		assetService.createAsset(asset);
	}
}
