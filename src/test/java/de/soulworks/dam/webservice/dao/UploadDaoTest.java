package de.soulworks.dam.webservice.dao;

import de.soulworks.dam.domain.Upload;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.UUID;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/ektorp-config.xml")
public class UploadDaoTest {
	@Autowired
	@Qualifier("uploadDao")
	UploadDao uploadDao;

	@Test
	public void testCreateUpload() {
		String uid = "upload." + UUID.randomUUID().toString();

		Upload upload = Upload.create()
				.setUid(uid)
				.setBucketUid("com.example.bucket");

		uploadDao.createUpload(upload);

		Assert.assertNotNull(upload.getId());
		Assert.assertNotNull(upload.getRevision());

		Upload u = uploadDao.getUpload(uid);
		Assert.assertNotNull(u);

		Assert.assertEquals(upload.getUid(), u.getUid());
		Assert.assertEquals(upload.getBucketUid(), u.getBucketUid());
	}
/*
	@Test
	public void testGetNotExistingUpload() {
		String uid = "upload." + UUID.randomUUID().toString();

		Upload upload = uploadDao.getUpload(uid);
		Assert.assertNull(upload);
	}
*/
	@Test( expected = IllegalStateException.class )
	public void testInvalidIdDupes() {
		String uid = "upload." + UUID.randomUUID().toString();

		// Create first upload
		Upload u1 = Upload.create().setUid(uid).setBucketUid("com.bucket");
		uploadDao.createUpload(u1);

		// Check if they really got persisted
		Assert.assertNotNull(u1.getId());
		Assert.assertNotNull(u1.getRevision());

		// Create a second upload which uid is identical to the first one
		Upload u2 = Upload.create().setUid(uid).setBucketUid("com.bucket");
		uploadDao.createUpload(u2);

		// Check if they really got persisted
		Assert.assertNotNull(u2.getId());
		Assert.assertNotNull(u2.getRevision());

		// We should fail here because of two records with same uid
		uploadDao.getUpload(uid);
	}

	@Test
	public void testUpdateUpload() {
		String uid = "upload." + UUID.randomUUID().toString();

		Upload upload = Upload.create()
				.setUid(uid)
				.setBucketUid("com.example.bucket");

		uploadDao.createUpload(upload);

		Assert.assertNotNull(upload.getId());
		Assert.assertNotNull(upload.getRevision());

		upload.setBucketUid("com.example.newbucket");

		uploadDao.updateUpload(upload);

		Upload u = uploadDao.getUpload(uid);

		Assert.assertEquals(upload.getBucketUid(), u.getBucketUid());
	}
}
