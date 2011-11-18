package de.soulworks.dam.webservice.dao.mock;

import de.soulworks.dam.domain.Asset;
import de.soulworks.dam.domain.AssetType;
import de.soulworks.dam.webservice.dao.AssetDao;

import java.util.*;

import org.springframework.stereotype.Repository;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@Repository
public class MockAssetDao implements AssetDao {
	private Map<String, Asset> assets = new HashMap<String, Asset>();
	
	public MockAssetDao() {
		Asset a;

		a = Asset.create().setUid("1")
				.setTitle("My asset", Locale.ENGLISH)
				.setTitle("Mein asset", Locale.GERMAN)
				.setDescription("My asset", Locale.ENGLISH)
				.setDescription("Mein asset", Locale.GERMAN)
				.setType(AssetType.VIDEO)
				.setBucketUid("com.example.bucket");
		assets.put(a.getUid(), a);

		a = Asset.create().setUid("2")
				.setTitle("Your asset", Locale.ENGLISH)
				.setTitle("Dein asset", Locale.GERMAN)
				.setDescription("Your asset", Locale.ENGLISH)
				.setDescription("Dein asset", Locale.GERMAN)
				.setType(AssetType.VIDEO)
				.setBucketUid("com.example.bucket");
		assets.put(a.getUid(), a);
	}

	public List<Asset> getAssets() {
		List<Asset> buckets = new ArrayList<Asset>(this.assets.size());
		Iterator it = this.assets.keySet().iterator();
		while(it.hasNext()) {
			buckets.add(this.assets.get(it.next()));
		}
		return buckets;
	}

	public Asset getAsset(String id) {
		return assets.get(id);
	}

	@Override
	public List<Asset> getAssetsOfBucket(String bucketUid) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public void createAsset(Asset asset) {
		Asset a = getAsset(asset.getUid());
		if(a == null) {
			assets.put(asset.getUid(), asset);
		}
	}

	public void updateAsset(Asset asset) {
		/*
		Asset a = getAsset(uid);
		if(a != null) {
			a.setTitles(asset.getTitles());
			a.setDescriptions(asset.getDescriptions());
		}
		*/
	}

	public void deleteAsset(Asset asset) {
		assets.remove(asset.getUid());
	}

}
