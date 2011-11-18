package de.soulworks.dam.webservice.service;

import de.soulworks.dam.domain.Bucket;
import de.soulworks.dam.domain.Upload;
import de.soulworks.dam.domain.UploadStatus;
import de.soulworks.dam.domain.validation.ValidationException;
import de.soulworks.dam.webservice.dao.BucketDao;
import de.soulworks.dam.webservice.dao.UploadDao;
import org.joda.time.Instant;
import org.joda.time.ReadableInstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@Service
public class UploadService {
	@Autowired
	@Qualifier("uploadDao")
	private UploadDao uploadDao;

	@Autowired
	@Qualifier("bucketDao")
	private BucketDao bucketDao;

	public List<Upload> getUploads() {
		return uploadDao.getUploads();
	}

	public Upload getUpload(String uploadUid) {
		return uploadDao.getUpload(uploadUid);
	}

	/**
	 * Generate an unique upload.
	 *
	 * Note: The upload won't get persisted, you have to do this on your own.
	 * 
	 * @return
	 */
	public Upload createUniqueUpload() {
		// Generate uid for the asset
		ReadableInstant now = new Instant();
		String id = Long.toString(now.getMillis());

		// @todo Implement URLResolver

		Upload upload = Upload.create()
				.setUid(id)
				.setUrl(URI.create("http://example.com/location/to/upload/to"))
				.setStatus(UploadStatus.PENDING);

		return upload;
	}

	public List<Upload> getUploadsOfBucket(Bucket bucket) {
		return uploadDao.getUploadsOfBucket(bucket.getUid());
	}

	public List<Upload> getUploadsOfBucket(String bucketUid) {
		Bucket bucket = bucketDao.getBucket(bucketUid);
		if(null == bucket) {
			throw new IllegalArgumentException("Invalid bucketUid");
		}
		return getUploadsOfBucket(bucket);
	}

	public void createUpload(Upload upload) {
		// Check wether an upload with that uid already exists
		Upload u = getUpload(upload.getUid());
		if (null != u) {
			throw new IllegalArgumentException("Ooops. There is already an upload with that id.");
		}

		try {
			// Validate the upload
			validateUpload(upload);

			// Create the upload
			uploadDao.createUpload(upload);
		} catch(ValidationException e) {
		}
	}

	public void deleteUpload(Upload upload) {
		Upload u = getUpload(upload.getUid());
		if (null != u) {
			throw new IllegalArgumentException("Ooops. There is no upload with that id.");
		}

		// Create the upload
		uploadDao.deleteUpload(upload);
	}

	public void updateUpload(String uid, Upload upload) {
		Upload u = getUpload(uid);
		if (null != u) {
			throw new IllegalArgumentException("Ooops. There is no upload with that id.");
		}

		try {
			// Validate the upload
			validateUpload(upload);

			// Bind data
			u.setBucketUid(upload.getBucketUid());

			uploadDao.updateUpload(u);
		} catch(ValidationException e) {
		}

	}

	protected void validateUpload(Upload upload) {
		//@todo Implement validation
	}

}
