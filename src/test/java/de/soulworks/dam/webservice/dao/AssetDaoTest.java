package de.soulworks.dam.webservice.dao;

import de.soulworks.dam.domain.Asset;
import de.soulworks.dam.domain.AssetType;
import de.soulworks.dam.domain.Bucket;
import de.soulworks.dam.domain.Customer;
import org.joda.time.Instant;
import org.joda.time.ReadableInstant;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/ektorp-config.xml")
public class AssetDaoTest {

	@Autowired
	@Qualifier("assetDao")
	AssetDao assetDao;

	@Autowired
	@Qualifier("bucketDao")
	BucketDao bucketDao;

	@Test
	public void testCreateAsset() {
		// Generate uid for the bucket
//		String uid = "asset." + UUID.randomUUID().toString();
		ReadableInstant now = new Instant();
		String uid = "asset." + now.getMillis();

		// Compose an asset
		Asset a1 = Asset.create()
				.setUid(uid)
				.setBucketUid("com.example.bucket")
				.setOriginator(Customer.create().setUid("customer"))
				.setType(AssetType.VIDEO)
				.setTitle("My asset", Locale.ENGLISH)
				.setTitle("Mein asset", Locale.GERMAN)
				.setDescription("My first asset", Locale.ENGLISH)
				.setDescription("Mein erstes Asset", Locale.GERMAN);

		// Store the asset
		assetDao.createAsset(a1);

		// Ensure that we got an id and revision
		Assert.assertNotNull(a1.getId());
		Assert.assertNotNull(a1.getRevision());

		// Retrieve the asset from the datastore
		Asset a2 = assetDao.getAsset(uid);

		// Did we get an asset?
		Assert.assertNotNull(a2);

		// Compare id and revision
		Assert.assertEquals(a1.getId(), a2.getId());
		Assert.assertEquals(a1.getRevision(), a2.getRevision());

		Assert.assertEquals(a1.getUid(), a2.getUid());
		Assert.assertEquals(a1.getOriginatorUid(), a2.getOriginatorUid());
		Assert.assertEquals(a1.getType(), a2.getType());

		Assert.assertEquals(a1.getTitle(Locale.ENGLISH), a2.getTitle(Locale.ENGLISH));
		Assert.assertEquals(a1.getTitle(Locale.GERMAN), a2.getTitle(Locale.GERMAN));

		Assert.assertEquals(a1.getDescription(Locale.ENGLISH), a2.getDescription(Locale.ENGLISH));
		Assert.assertEquals(a1.getDescription(Locale.GERMAN), a2.getDescription(Locale.GERMAN));
	}

	@Test
	public void testUpdateAsset() {
		// Generate uid for the asset
		ReadableInstant now = new Instant();
		String uid = "asset." + now.getMillis();

		Asset a1 = Asset.create()
				.setUid(uid)
				.setType(AssetType.VIDEO)
				.setOriginator(Customer.create().setUid("customer"))
				.setTitle("My asset", Locale.ENGLISH)
				.setTitle("Mein asset", Locale.GERMAN)
				.setDescription("My first asset", Locale.ENGLISH)
				.setDescription("Mein erstes Asset", Locale.GERMAN)
				.setBucketUid("com.example.bucket");

		// Store the asset
		assetDao.createAsset(a1);

		// Check if we got an id and revision
		Assert.assertNotNull(a1.getId());
		Assert.assertNotNull(a1.getRevision());

		// Remember the revision
		String oldRev = a1.getRevision();

		// Recompose asset
		a1.setType(AssetType.IMAGE)
				.setBucketUid("com.example.bucket.new")
				.setOriginator(Customer.create().setUid("new.customer"))
				.setTitle("My new asset", Locale.ENGLISH)
				.setTitle("Mein neues asset", Locale.GERMAN)
				.setDescription("My first and new asset", Locale.ENGLISH)
				.setDescription("Mein erstes und neues Asset", Locale.GERMAN);

		// Update the asset
		assetDao.updateAsset(a1);

		// Ensure that the revision got updated
		Assert.assertNotSame(oldRev, a1.getRevision());

		// Load that asset again
		Asset a2 = assetDao.getAsset(uid);

		Assert.assertEquals(a1.getUid(), a2.getUid());
		Assert.assertEquals(a1.getBucketUid(), a2.getBucketUid());
		Assert.assertEquals(a1.getTitle(Locale.ENGLISH), a2.getTitle(Locale.ENGLISH));
		Assert.assertEquals(a1.getTitle(Locale.GERMAN), a2.getTitle(Locale.GERMAN));
		Assert.assertEquals(a1.getDescription(Locale.ENGLISH), a2.getDescription(Locale.ENGLISH));
		Assert.assertEquals(a1.getDescription(Locale.GERMAN), a2.getDescription(Locale.GERMAN));
	}

	@Test
	public void testDeleteAsset() {
		// Generate uid for the asset
		ReadableInstant now = new Instant();
		String uid = "asset." + now.getMillis();

		// Compose an asset
		Asset a1 = Asset.create().setUid(uid);

		// Store the asset
		assetDao.createAsset(a1);

		// Check for id and revision
		Assert.assertNotNull(a1.getId());
		Assert.assertNotNull(a1.getRevision());

		// Delete the asset
		assetDao.deleteAsset(a1);

		// Try to retrieve the asset
		Asset a2 = assetDao.getAsset(uid);

		// There shouldn't be any asset
		Assert.assertNull(a2);
	}

	@Test
	public void testGetAsset() {
		// Generate uid for the asset
		ReadableInstant now = new Instant();
		String uid = "asset." + now.getMillis();

		// Create an asset instance
		Asset a1 = Asset.create().setUid(uid);

		// Store the asset
		assetDao.createAsset(a1);

		// Retrieve the asset from the db
		Asset a2 = assetDao.getAsset(uid);

		// We should have get an asset
		Assert.assertNotNull(a2);

		// Compare revisions of the both, which should be the same
		Assert.assertEquals(a1.getId(), a2.getId());
		Assert.assertEquals(a1.getRevision(), a2.getRevision());

		// Both assets should be equal
		Assert.assertTrue(a1.equals(a2));
	}

	@Test
	public void testGetAssetsOfBucket() {
		ReadableInstant now = new Instant();

		// Create a bucket at the db
		Bucket bucket = Bucket.create().setUid("com.example.bucket."+now.getMillis());
		bucketDao.createBucket(bucket);

		// Create an asset at the bucket
		Asset a1 = Asset.create().setUid("asset."+now.getMillis())
				.setTitle("My asset", Locale.ENGLISH)
				.setTitle("Mein asset", Locale.GERMAN)
				.setDescription("My first asset", Locale.ENGLISH)
				.setDescription("Mein erstes Asset", Locale.GERMAN)
				.setBucketUid(bucket.getUid());
		assetDao.createAsset(a1);

		// Create another asset at the bucket
		Asset a2 = Asset.create().setUid("asset2."+now.getMillis())
				.setTitle("My asset", Locale.ENGLISH)
				.setTitle("Mein asset", Locale.GERMAN)
				.setDescription("My first asset", Locale.ENGLISH)
				.setDescription("Mein erstes Asset", Locale.GERMAN)
				.setBucketUid(bucket.getUid());
		assetDao.createAsset(a2);

		// Retrieve all assets of the bucket
		List<Asset> assets = assetDao.getAssetsOfBucket(bucket.getUid());

		// There should be 2 assets at the bucket
		Assert.assertEquals(2, assets.size());
	}

}
