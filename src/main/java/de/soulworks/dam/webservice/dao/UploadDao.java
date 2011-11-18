package de.soulworks.dam.webservice.dao;

import de.soulworks.dam.domain.Upload;

import java.util.List;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
public interface UploadDao {
	public Upload getUpload(String uploadUid);

	public List<Upload> getUploads();

	public List<Upload> getUploadsOfBucket(String bucketUid);

	public void createUpload(Upload upload);

	public void updateUpload(Upload upload);

	public void deleteUpload(Upload upload);
}
