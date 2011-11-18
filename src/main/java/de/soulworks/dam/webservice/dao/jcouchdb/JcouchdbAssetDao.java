package de.soulworks.dam.webservice.dao.jcouchdb;

import de.soulworks.dam.domain.Asset;
import de.soulworks.dam.domain.Bucket;
import de.soulworks.dam.webservice.dao.BucketDao;
import de.soulworks.dam.webservice.dao.AssetDao;
import org.jcouchdb.db.Database;
import org.jcouchdb.document.ValueAndDocumentRow;
import org.jcouchdb.document.ViewAndDocumentsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
public class JcouchdbAssetDao implements AssetDao {
	@Autowired
	private Database systemDatabase;

	@Autowired
	@Qualifier("bucketDao")
	private BucketDao bucketDao;
	
	public void init() {
		String[] uids = new String[]{
				"com.example.project",
				"com.example.mobile.iphone",
				"com.example.mobile.android",
				"com.customer"};

		for(String uid: uids) {
			Bucket bucket = Bucket.create()
					.setUid(uid)
					.setName("My bucket " + uid, Locale.GERMAN)
					.setDescription("This is my bucket " + uid, Locale.GERMAN);
			bucketDao.createBucket(bucket);

			int i = 100;
			while(i > 0) {
				Asset asset = Asset.create()
						.setBucketUid(uid)
						.setTitle("Asset #"+i+" of Bucket "+uid, Locale.GERMAN)
						.setDescription("Asset #"+i+" of Bucket "+uid, Locale.GERMAN);
				createAsset(asset);
				i--;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Asset> getAssets() {
		ViewAndDocumentsResult<Object, Asset> result = systemDatabase.queryViewAndDocuments("asset/byUid", Object.class, Asset.class, null, null);

		// Iterate over all rows and collect assets in list
		List<Asset> assets = new ArrayList<Asset>();
		for (ValueAndDocumentRow<Object, Asset> row : result.getRows()) {
			assets.add(row.getDocument());
		}

		// Return collected assets
		return assets;
	}

	/**
	 * {@inheritDoc}
	 */
	public Asset getAsset(String id) {
		return systemDatabase.getDocument(Asset.class, id, null, null);
	}

	@Override
	public List<Asset> getAssetsOfBucket(String bucketUid) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	/**
	 * {@inheritDoc}
	 */
	public void createAsset(Asset asset) {
		systemDatabase.createDocument(asset);
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateAsset(Asset asset) {
		systemDatabase.updateDocument(asset);
	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteAsset(Asset asset) {
		systemDatabase.delete(asset);
	}
}
