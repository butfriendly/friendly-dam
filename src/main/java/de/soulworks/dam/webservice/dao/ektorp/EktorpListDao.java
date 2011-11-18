package de.soulworks.dam.webservice.dao.ektorp;

import de.soulworks.dam.domain.List;
import de.soulworks.dam.webservice.dao.ListDao;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author Christian Schmitz <csc@soulworks.de
 */
@Component
@View( name="all", map = "function(doc) { if ('List' == doc.documentType) { emit(doc.uid, doc._id) } }")
public class EktorpListDao extends CouchDbRepositorySupport<List> implements ListDao {
	private static final Logger log = LoggerFactory.getLogger(EktorpListDao.class);

	@Autowired
	public EktorpListDao(@Qualifier("ektorpDatabase") CouchDbConnector db) {
		super(List.class, db);

		// Trigger automatic view generation
		initStandardDesignDocument();
	}

	public java.util.List<List> findAll() {
		ViewQuery q = createQuery("all").includeDocs(true);
		return db.queryView(q, List.class);
	}

	private java.util.List<List> findByUid(String uid) {
		ViewQuery q = createQuery("all").includeDocs(true).key(uid);
		return db.queryView(q, List.class);
	}

	@GenerateView
	private java.util.List<List> findByBucketUid(String bucketUid) {
		return queryView("by_bucketUid", bucketUid);
	}

	@Override
	public List getList(String listUid) {
		return findByUid(listUid).get(0);
	}

	@Override
	public java.util.List<List> getLists() {
		return findAll();
	}

	@Override
	public java.util.List<List> getListsOfBucket(String bucketUid) {
		return findByBucketUid(bucketUid);
	}

	@Override
	public void createList(List list) {
		db.create(list);
	}

	@Override
	public void updateList(List list) {
		db.update(list);
	}

	@Override
	public void deleteList(List list) {
		db.delete(list.getId(), list.getRevision());
	}
}
