package de.soulworks.dam.domain;

import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
public class AssetTest {

	@Test
	public void testConstructWithParam() {
		String id = "id";
		Asset a = new Asset(id);
		Assert.assertSame(id, a.getUid());
	}

	@Test
	public void testCreate() {
		Asset asset = Asset.create();

		Assert.assertEquals("Asset", asset.getClass().getSimpleName());
	}

	@Test
	public void testTitleLocalization() {
		Asset asset = Asset.create()
		                   .setTitle("title", Locale.ENGLISH)
		                   .setTitle("titel", Locale.GERMAN);

		Assert.assertEquals("title", asset.getTitle(Locale.ENGLISH));
		Assert.assertEquals("titel", asset.getTitle(Locale.GERMAN));

		Assert.assertNull(asset.getDescription(Locale.CHINESE));
	}

	@Test
	public void testDescriptionLocalization() {
		Asset asset = Asset.create()
		                   .setDescription("description", Locale.ENGLISH)
		                   .setDescription("beschreibung", Locale.GERMAN);

		Assert.assertEquals("description", asset.getDescription(Locale.ENGLISH));
		Assert.assertEquals("beschreibung", asset.getDescription(Locale.GERMAN));

		Assert.assertNull(asset.getTitle(Locale.CHINESE));
	}

	@Test
	public void testSetAndGetOriginator() {
		Asset asset = Asset.create().setOriginator(Customer.create().setUid("Customer"));
		Assert.assertEquals("Customer", asset.getOriginatorUid());
	}

	@Test
	public void testSetAndGetBucket() {
		Asset asset = Asset.create().setBucket(Bucket.create().setUid("Bucket"));
		Assert.assertEquals("Bucket", asset.getBucketUid());
	}

	@Test
	public void testSetAndGetMediaId() {
		long mediaId = 0xACE882;
		Asset asset = Asset.create().setMediaId(mediaId);
		Assert.assertEquals(mediaId, asset.getMediaId());
	}

	@Test
	public void testClone() {
		// Create an asset
		Asset a1 = Asset.create()
				.setUid("asset1")
				.setBucketUid("de.soulworks.bucket")
				.setTitle("title", Locale.ENGLISH)
				.setTitle("titel", Locale.GERMAN)
				.setDescription("description", Locale.ENGLISH)
				.setDescription("beschreibung", Locale.GERMAN);

		// Clone the previously created asset
		Asset a2 = a1.clone();

		Assert.assertEquals(a1.getUid(), a2.getUid());
		Assert.assertEquals(a1.getBucketUid(), a2.getBucketUid());

		Assert.assertEquals(a1.getTitle(Locale.ENGLISH), a2.getTitle(Locale.ENGLISH));
		Assert.assertEquals(a1.getTitle(Locale.GERMAN), a2.getTitle(Locale.GERMAN));
		Assert.assertNotSame(a1.getTitles(), a2.getTitles());

		Assert.assertEquals(a1.getDescription(Locale.ENGLISH), a2.getDescription(Locale.ENGLISH));
		Assert.assertEquals(a1.getDescription(Locale.GERMAN), a2.getDescription(Locale.GERMAN));
		Assert.assertNotSame(a1.getDescriptions(), a2.getDescriptions());
	}
}
