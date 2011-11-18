package de.soulworks.dam.webservice.dao;

import de.soulworks.dam.domain.Bucket;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/ektorp-config.xml")
public class BucketDaoTest {

	@Autowired
	@Qualifier("bucketDao")
	BucketDao bucketDao;
/*
	@Test
	@ExpectedException(DocumentNotFoundException.class)
	public void testFailingFindById() {
		// Lookup non-existing bucket, which should fail
		Bucket b = bucketDao.getBucket("bla");

		Assert.assertNull(b);
	}
*/
	@Test
	public void testGetBucket() {
		// Generate uid for the bucket
		String uid = "com.example.bucket." + UUID.randomUUID().toString();

		// Create a bucket
		Bucket b1 = Bucket.create().setUid(uid);

		// Store the bucket
		bucketDao.createBucket(b1);

		Bucket b2 = bucketDao.getBucket(uid);

		Assert.assertNotNull(b2);
		Assert.assertEquals(b1.getUid(), b2.getUid());
	}

	@Test
	public void testCreateBucket() {
		Assert.assertEquals("EktorpBucketDao", bucketDao.getClass().getSimpleName());

		// Prepare constraints
/*		SortedMap<Date, String> constraints = new TreeMap<Date, String>(){
			{
				put(new Date(), "var init = 1;");
			}
		};
*/
		// Generate uid for the bucket
		String uid = "com.example.bucket." + UUID.randomUUID().toString();

		// Create a bucket
		Bucket b1 = Bucket.create().setUid(uid)
//			.setConstraints(constraints)
			.setName("My name", Locale.ENGLISH)
			.setName("Mein Name", Locale.GERMAN)
			.setDescription("My description", Locale.ENGLISH)
			.setDescription("Meine Beschreibung", Locale.GERMAN);

		// Store the bucket
		bucketDao.createBucket(b1);

		// Retrieve the bucket from datastore
		Bucket b2 = bucketDao.getBucket(uid);

		// Check, wether we found a bucket or not
		Assert.assertNotNull(b2);

		Assert.assertEquals(b1.getUid(), b2.getUid());

		Assert.assertEquals(b1.getName(Locale.ENGLISH), b2.getName(Locale.ENGLISH));
		Assert.assertEquals(b1.getName(Locale.GERMAN), b2.getName(Locale.GERMAN));

		Assert.assertEquals(b1.getDescription(Locale.ENGLISH), b2.getDescription(Locale.ENGLISH));
		Assert.assertEquals(b1.getDescription(Locale.GERMAN), b2.getDescription(Locale.GERMAN));
	}

	@Test
	public void testUpdateBucket() {
		// Generate uid for the bucket
		String uid = "com.example.bucket." + UUID.randomUUID().toString();

		// Create a bucket
		Bucket b1 = Bucket.create().setUid(uid)
			.setName("My name", Locale.ENGLISH)
			.setName("Mein Name", Locale.GERMAN);

		// Store the bucket
		bucketDao.createBucket(b1);

		// We should have a revision, now
		Assert.assertNotNull(b1.getRevision());

		b1.setName("My new name", Locale.ENGLISH)
			.setName("Mein neuer Name", Locale.GERMAN);

		// Update bucket
		bucketDao.updateBucket(b1);
		
		// Try to retrieve the bucket from datastore
		Bucket b2 = bucketDao.getBucket(uid);

		Assert.assertEquals(b1.getId(), b2.getId());
		Assert.assertNotSame(b1.getRevision(), b2.getRevision());
		
		Assert.assertEquals("Mein neuer Name", b2.getName(Locale.GERMAN));
		Assert.assertEquals("My new name", b2.getName(Locale.ENGLISH));
	}

	@Test
	public void testDeleteBucket() {
		// Generate uid for the bucket
		String uid = "com.example.bucket." + UUID.randomUUID().toString();

		// Create a bucket
		Bucket b1 = Bucket.create().setUid(uid)
			.setName("My name", Locale.ENGLISH)
			.setName("Mein Name", Locale.GERMAN);

		// Store the bucket
		bucketDao.createBucket(b1);

		// We should have a revision now
		Assert.assertNotNull(b1.getRevision());

		// Try to retrieve the bucket from datastore
		Bucket b2 = bucketDao.getBucket(uid);

		// We should have received a bucket from the datastore
		Assert.assertNotNull(b2);
		Assert.assertEquals(b1.getId(), b2.getId());
		Assert.assertEquals(b1.getRevision(), b2.getRevision());
/*
		// Delete the bucket
		bucketDao.deleteBucket(b1);

		Assert.assertTrue(b1.isDeleted());

		// Try to retrieve the bucket from datastore
		Bucket b3 = bucketDao.getBucket(uid);

		// Check, wether we found a bucket or not
		Assert.assertNull(b3);
*/
	}

	@Test
	public void testStoringProfiles() {
		// Generate uid for the bucket
		String uid = "com.example.bucket." + UUID.randomUUID().toString();

		List<String> profiles = Arrays.asList("Buenos Aires", "Cordoba", "La Plata");
		
		Bucket bucket = Bucket.create().setUid(uid).setProfiles(profiles);
	}
}
