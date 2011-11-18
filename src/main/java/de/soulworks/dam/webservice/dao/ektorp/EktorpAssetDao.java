package de.soulworks.dam.webservice.dao.ektorp;

import de.soulworks.dam.domain.Asset;
import de.soulworks.dam.webservice.dao.AssetDao;
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
 * @author Christian Schmitz <csc@soulworks.de>
 */
@Component
@View( name="all", map = "function(doc) { if ('Asset' == doc.documentType) { emit(doc.uid, doc._id) } }")
public class EktorpAssetDao extends CouchDbRepositorySupport<Asset> implements AssetDao{
	private static final Logger log = LoggerFactory.getLogger(EktorpAssetDao.class);

	@Autowired
	public EktorpAssetDao(@Qualifier("ektorpDatabase") CouchDbConnector db) {
		super(Asset.class, db);

		// Trigger automatic view generation
		initStandardDesignDocument();
	}

	@Override
	public List<Asset> getAssets() {
		ViewQuery q = createQuery("all").includeDocs(true);
		return db.queryView(q, Asset.class);
	}

	private List<Asset> findByUid(String uid) {
		ViewQuery q = createQuery("all").includeDocs(true).key(uid);
		return db.queryView(q, Asset.class);
	}

	@GenerateView	
	private List<Asset> findByBucketUid(String bucketUid) {
		return queryView("by_bucketUid", bucketUid);
	}

	@GenerateView
	private List<Asset> findByOriginatorUid(String originatorUid) {
		return queryView("by_originatorUid", originatorUid);
	}

	public List<Asset> getAssetsOfBucket(String bucketUid) {
		return findByBucketUid(bucketUid);
	}

	@Override
	public Asset getAsset(String uid) {
		Asset asset = null;
		try {
			List<Asset> assets = findByUid(uid);
			if (assets.size() == 1) {
				asset = assets.get(0);
			} else if (assets.size() > 1) {
				log.info("We found {} assets when looking for {}", assets.size(), uid);
				throw new IllegalStateException("We found more than 1 matching asset.");
			}
		} catch(DocumentNotFoundException e) {
			log.info("The bucket {} does not exist.", uid);
		}
		return asset;
	}

	@Override
	public void createAsset(Asset asset) {
		db.create(asset);
	}

	@Override
	public void updateAsset(Asset asset) {
		db.update(asset);
	}

	@Override
	public void deleteAsset(Asset asset) {
		db.delete(asset.getId(), asset.getRevision());
	}
}
