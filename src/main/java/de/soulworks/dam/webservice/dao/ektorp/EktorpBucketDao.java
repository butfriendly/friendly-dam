package de.soulworks.dam.webservice.dao.ektorp;

import de.soulworks.dam.domain.Bucket;
import de.soulworks.dam.webservice.dao.BucketDao;
import org.ektorp.CouchDbConnector;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.ViewQuery;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * User: christian.schmitz
 * Date: 04.08.2010
 * Time: 14:35:07
 */
@Component
@View( name="all", map = "function(doc) { if ('Bucket' == doc.documentType) { emit(doc.uid, doc._id) } }")
public class EktorpBucketDao extends CouchDbRepositorySupport<Bucket> implements BucketDao {
	private static final Logger log = LoggerFactory.getLogger(EktorpBucketDao.class);

	@Autowired
	public EktorpBucketDao(@Qualifier("ektorpDatabase") CouchDbConnector db) {
		super(Bucket.class, db);

		// Trigger automatic view generation
		initStandardDesignDocument();
	}

	@GenerateView
	private List<Bucket> findByCustomerUid(String customerUid) {
		return queryView("by_customerUid", customerUid);
	}

	private List<Bucket> findByUid(String uid) {
		ViewQuery q = createQuery("all").includeDocs(true).key(uid);
		return db.queryView(q, Bucket.class);
	}

	private List<Bucket> findAll() {
		ViewQuery q = createQuery("all").includeDocs(true);
		return db.queryView(q, Bucket.class);
	}

	public List<Bucket> getBuckets() {
		return findAll();
	}

	public Bucket getBucket(String uid) {
		Bucket bucket = null;
		try {
			List<Bucket> buckets = findByUid(uid);
			if (buckets.size() == 1) {
				bucket = buckets.get(0);
			} else if (buckets.size() > 1) {
				log.info("We found {} buckets when looking for {}", buckets.size(), uid);
				throw new IllegalStateException("We found more than 1 matching bucket.");
			}
		} catch(DocumentNotFoundException e) {
			log.info("The bucket {} does not exist.", uid);
		}
		return bucket;
	}

	public void createBucket(Bucket bucket) {
		db.create(bucket);
	}

	public void updateBucket(Bucket bucket) {
		db.update(bucket);
	}

	public void deleteBucket(Bucket bucket) {
		db.delete(bucket.getId(), bucket.getRevision());
	}
}
