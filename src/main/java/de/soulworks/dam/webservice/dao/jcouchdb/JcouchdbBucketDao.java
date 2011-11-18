package de.soulworks.dam.webservice.dao.jcouchdb;

import de.soulworks.dam.domain.Bucket;
import de.soulworks.dam.webservice.dao.BucketDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jcouchdb.db.Database;
import org.jcouchdb.document.ValueAndDocumentRow;
import org.jcouchdb.document.ViewAndDocumentsResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@Repository
public class JcouchdbBucketDao implements BucketDao {
	private static final Logger log = LoggerFactory.getLogger(JcouchdbBucketDao.class);

	@Autowired
	private Database systemDatabase;

	/**
	 * {@inheritDoc}
	 */
	public Bucket getBucket(String uid) {
		// Retrieve Buckets matching the uid
		ViewAndDocumentsResult<Object, Bucket> result = systemDatabase.queryViewAndDocumentsByKeys(
			"bucket/byUid", Object.class, Bucket.class, Arrays.asList(uid), null, null);

		// We didn't find any bucket with the given uid
		if(result.getRows().isEmpty()) {
			return null;
		}

		// We found more than one bucket with that uid, which shouldn't happen!
		if(result.getRows().size() > 1) {
			log.error("Found multiple bucket with id {}", uid);
			return null;
		}
		
		return result.getRows().get(0).getDocument();
	}

	/**
	 * {@inheritDoc}
	 */
	public void createBucket(Bucket bucket) {
		// Check for an uid
		if(bucket.getUid().isEmpty()) {
			throw new IllegalStateException("No unique-identifier found, but a bucket requires one.");
		}
		
		// Check for an unique uid
		if(getBucket(bucket.getUid()) != null) {
			throw new IllegalStateException("The unique-identifier of a bucket has to be _unique_.");
		}

		// Create the document at the database
		systemDatabase.createDocument(bucket);
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateBucket(Bucket bucket) {
		/*
		if(getBucket(uid) == null) {
			throw new IllegalStateException("The bucket "+uid+" does not exist.");
		}
		*/

		systemDatabase.updateDocument(bucket);
	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteBucket(Bucket bucket) {
		if(getBucket(bucket.getUid()) == null) {
			throw new IllegalStateException("The bucket "+bucket.getUid()+" does not exist.");
		}

		systemDatabase.delete(bucket);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Bucket> getBuckets() {
		// Search for buckets at the couch
		ViewAndDocumentsResult<Object, Bucket> result = systemDatabase.queryViewAndDocuments(
				"bucket/byUid", Object.class, Bucket.class, null, null);

		log.info("Found {} buckets", result.getTotalRows());

		// Iterate over all rows and collect buckets in a list
		List<Bucket> buckets = new ArrayList<Bucket>();
		for (ValueAndDocumentRow<Object, Bucket> row : result.getRows()) {
			Bucket bucket = row.getDocument();
			log.info("Found bucket {} at result", bucket.getUid());
			buckets.add(bucket);
		}

		// Et voila, our buckets!
		return buckets;
	}

}
