package de.soulworks.dam.webservice.dao.ektorp;

import de.soulworks.dam.domain.Upload;
import de.soulworks.dam.webservice.dao.UploadDao;
import org.ektorp.CouchDbConnector;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.ViewQuery;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@Component
@View( name="all", map = "function(doc) { if ('Upload' == doc.documentType) { emit(doc.uid, doc._id) } }")
public class EktorpUploadDao extends CouchDbRepositorySupport<Upload> implements UploadDao {
	private static final Logger log = LoggerFactory.getLogger(EktorpUploadDao.class);

	@Autowired
	public EktorpUploadDao(@Qualifier("ektorpDatabase") CouchDbConnector db) {
		super(Upload.class, db);

		// Trigger automatic view generation
		initStandardDesignDocument();
	}

	/**
	 * Retrieves all available uploads.
	 * 
	 * @return
	 */
	private List<Upload> findAll() {
		List<Upload> uploads = null;
		try {
			ViewQuery q = createQuery("all").includeDocs(true);
			uploads = db.queryView(q, Upload.class);
		} catch(DocumentNotFoundException e) {
		}
		return uploads;
	}

	/**
	 * Retrieves a single upload by its {@code uid}.
	 * @param uid
	 * @return
	 */
	private Upload findByUid(String uid) {
		Upload upload = null;
		try {
			ViewQuery q = createQuery("all").includeDocs(true).key(uid);
			List<Upload> uploads = db.queryView(q, Upload.class);

			log.info("We found {} uploads when looking for {}.", uploads.size(), uid);

			if (uploads.size() == 1) {
				upload = uploads.get(0);
			}
			else if (uploads.size() > 1) {
				throw new IllegalStateException("We found more than 1 matching upload.");
			}

		} catch(DocumentNotFoundException e) {
		}
		return upload;
	}

	/**
	 * Retrieves all uploads of the specified bucket.
	 * 
	 * @param bucketUid
	 * @return
	 */
	@View( name="by_bucket_uid", map = "function(doc) { if ('Upload' == doc.documentType && doc.bucketUid) { emit(doc.bucketUid, doc._id) } }")
	private List<Upload> findByBucketUid(String bucketUid) {
		List<Upload> uploads = null;
		try {
			ViewQuery q = createQuery("by_bucket_uid").includeDocs(true).key(bucketUid);
			uploads = db.queryView(q, Upload.class);
		} catch(DocumentNotFoundException e) {
		}
		return uploads;
	}

	@Override
	public Upload getUpload(String uid) {
		return findByUid(uid);
	}

	@Override
	public List<Upload> getUploads() {
		return findAll();
	}

	@Override
	public List<Upload> getUploadsOfBucket(String bucketUid) {
		return findByBucketUid(bucketUid);
	}

	@Override
	public void createUpload(Upload upload) {
		db.create(upload);
	}

	@Override
	public void updateUpload(Upload upload) {
		db.update(upload);
	}

	@Override
	public void deleteUpload(Upload upload) {
		db.delete(upload.getId(), upload.getRevision());
	}
}
