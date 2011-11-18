package de.soulworks.dam.domain;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Locale;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
public class ListTest {
	@Test
	public void testConstruct() {
		List list = new List();
		Assert.assertEquals("de.soulworks.dam.domain.List", list.getClass().getName());
	}

	@Test
	public void testNameInternationalization() {
		List list = List.create()
		                .setName("Mein Name", Locale.GERMAN)
		                .setName("My name", Locale.ENGLISH);

		// Should pass, because we set the names before
		Assert.assertEquals("Mein Name", list.getName(Locale.GERMAN));
		Assert.assertEquals("My name", list.getName(Locale.ENGLISH));

		// Should pass, because we don't have any chinese translation
		Assert.assertNull(list.getName(Locale.CHINESE));
	}

	@Test
	public void testDescriptionInternationalization() {
		List list = List.create()
		                .setDescription("Meine Beschreibung", Locale.GERMAN)
		                .setDescription("My description", Locale.ENGLISH);

		// Should pass, because we set the desciptions before
		Assert.assertEquals("Meine Beschreibung", list.getDescription(Locale.GERMAN));
		Assert.assertEquals("My description", list.getDescription(Locale.ENGLISH));

		// Should pass, because we don't have any chinese translation
		Assert.assertNull(list.getDescription(Locale.CHINESE));
	}

	@Test
	public void testSetAndGetBucketId() {
		List list = List.create().setBucketUid("bucketuid");

		Assert.assertEquals("bucketuid", list.getBucketUid());
	}
}
