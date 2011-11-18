package de.soulworks.dam.webservice.service;

import de.soulworks.dam.domain.Bucket;
import de.soulworks.dam.webservice.ServiceTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.ExpectedException;

import java.util.Locale;
import java.util.UUID;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
public class BucketServiceTest extends ServiceTest {
	@Autowired
	private BucketService bucketService;

	@Test
	public void testCreateBucket() {
		String uid = this.getClass().getSimpleName() +"."+ UUID.randomUUID().toString();

		// Create a new bucket instance
		Bucket bucket = Bucket.create()
				.setUid(uid)
				.setName("My name", Locale.ENGLISH)
				.setDescription("My desc", Locale.ENGLISH);

		// Put the bucket at the db
		bucketService.createBucket(bucket);

		// Check for an id and revision
		Assert.assertNotNull(bucket.getId());
		Assert.assertNotNull(bucket.getRevision());
	}

	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void testCreateBucketDupe() {
		String uid = this.getClass().getSimpleName() +"."+ UUID.randomUUID().toString();

		// Create a new bucket instance
		Bucket bucket = Bucket.create()
				.setUid(uid)
				.setName("My name", Locale.ENGLISH)
				.setDescription("My desc", Locale.ENGLISH);

		// Put the bucket at the db
		bucketService.createBucket(bucket);

		// Check for an id and revision
		Assert.assertNotNull(bucket.getId());
		Assert.assertNotNull(bucket.getRevision());

		// Try to create the same bucket again, which should fail
		bucketService.createBucket(bucket);
	}

	@Test
	public void testUpdateBucket() {
		String uid = this.getClass().getSimpleName() +"."+ UUID.randomUUID().toString();

		// Create a new bucket instance
		Bucket bucket = Bucket.create()
				.setUid(uid)
				.setName("My name", Locale.ENGLISH)
				.setDescription("My desc", Locale.ENGLISH);

		// Put the bucket at the db
		bucketService.createBucket(bucket);

		// Check for an id and revision
		Assert.assertNotNull(bucket.getId());
		Assert.assertNotNull(bucket.getRevision());

		// Remember the revision
		String revision = bucket.getRevision();

		// Change name and description of the bucket
		bucket.setName("Name", Locale.ENGLISH)
				.setDescription("Desc", Locale.ENGLISH);

		// Update the bucket at the db
		bucketService.updateBucket(bucket.getUid(), bucket);

		// We should have get a new revision
		// Obviously ektorp does not update revision-property after update
//		Assert.assertNotSame(revision, bucket.getRevision());

		// Reload the bucket from db
		Bucket reloadedBucket = bucketService.getBucket(uid);

		// Obviously ektorp does not update revision-property after update
//		Assert.assertEquals(bucket.getRevision(), reloadedBucket.getRevision());

		// Check for changes
		Assert.assertEquals(bucket.getName(Locale.ENGLISH), reloadedBucket.getName(Locale.ENGLISH));
		Assert.assertEquals(bucket.getDescription(Locale.ENGLISH), reloadedBucket.getDescription(Locale.ENGLISH));
	}

	@Test
	public void testDeleteBucket() {
		String uid = this.getClass().getSimpleName() +"."+ UUID.randomUUID().toString();

		// Create a new bucket instance
		Bucket bucket = Bucket.create()
				.setUid(uid)
				.setName("My name", Locale.ENGLISH)
				.setDescription("My desc", Locale.ENGLISH);

		// Put the bucket at the db
		bucketService.createBucket(bucket);

		// Check for an id and revision
		Assert.assertNotNull(bucket.getId());
		Assert.assertNotNull(bucket.getRevision());

		bucketService.deleteBucket(bucket);
	}
}
