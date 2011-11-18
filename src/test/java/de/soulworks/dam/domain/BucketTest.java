package de.soulworks.dam.domain;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.List;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
public class BucketTest {

	@Test
	public void testConstructWithParams() {
		String id = "id";
		Bucket b = new Bucket(id);
		Assert.assertSame(id, b.getUid());
	}

	@Test
	public void testCreate() {
		Bucket bucket = Bucket.create();
		Assert.assertEquals("Bucket", bucket.getClass().getSimpleName());
	}

	@Test
	public void testConstruct() {
		Bucket bucket = Bucket.create();
		Assert.assertEquals("Bucket", bucket.getClass().getSimpleName());
	}

	@Test
	public void testNameInternationalization() {
		Bucket bucket = Bucket.create()
		                      .setName("Mein Name", Locale.GERMAN)
		                      .setName("My name", Locale.ENGLISH);

		// Should pass, because we set the names before
		Assert.assertEquals("Mein Name", bucket.getName(Locale.GERMAN));
		Assert.assertEquals("My name", bucket.getName(Locale.ENGLISH));

		// Should pass, because we don't have any chinese translation
		Assert.assertNull(bucket.getName(Locale.CHINESE));
	}

	@Test
	public void testDescriptionInternationalization() {
		Bucket bucket = Bucket.create()
		                      .setDescription("Meine Beschreibung", Locale.GERMAN)
		                      .setDescription("My description", Locale.ENGLISH);

		// Should pass, because we set the desciptions before
		Assert.assertEquals("Meine Beschreibung", bucket.getDescription(Locale.GERMAN));
		Assert.assertEquals("My description", bucket.getDescription(Locale.ENGLISH));

		// Should pass, because we don't have any chinese translation
		Assert.assertNull(bucket.getDescription(Locale.CHINESE));
	}

	@Test
	public void testSetAndGetProfile() {
		List<String> profiles = Arrays.asList("Auto", "Zug");
		Bucket bucket = Bucket.create()
				.setProfiles(profiles);

		Assert.assertEquals(profiles, bucket.getProfiles());
	}
/*
	@Test
	public void testConstraints() {
		SortedMap<Date, String> constraints = new TreeMap<Date, String>(){
			{
				put(new Date(), "var init = 1;");
				put(new Date(), "var init = 2;");
				put(new Date(), "var init = 3;");
			}
		};
		
		Bucket bucket = Bucket.create()
				.setUid("de.soulworks.bucket")
				.setConstraints(constraints);

		assertEquals(3, bucket.getConstraints().size());
	}
*/
}
