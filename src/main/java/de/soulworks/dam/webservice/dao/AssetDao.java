package de.soulworks.dam.webservice.dao;

import de.soulworks.dam.domain.Asset;

import java.util.List;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
public interface AssetDao {
	public List<Asset> getAssets();

	public Asset getAsset(String id);

	public List<Asset> getAssetsOfBucket(String bucketUid);

	public void createAsset(Asset asset);

	public void updateAsset(Asset asset);

	public void deleteAsset(Asset asset);
}
