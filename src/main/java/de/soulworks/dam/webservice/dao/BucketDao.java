package de.soulworks.dam.webservice.dao;


import de.soulworks.dam.domain.Bucket;

import java.util.List;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
public interface BucketDao {
	/**
	 * Retrieves all existing buckets
	 *
	 * @return
	 */
	public List<Bucket> getBuckets();

	/**
	 * Retrieves a bucket by its unique identifier from the storage.
	 *
	 * @param uid Uid of the bucket to find
	 * @return
	 */
	public Bucket getBucket(String uid);

	/**
	 * Saves the given {@code bucket} at the storage.
	 *
	 * @param bucket
	 */
	public void createBucket(Bucket bucket);

	/**
	 * Updates the {@code bucket} at the storage.
	 * @param bucket
	 */
	public void updateBucket(Bucket bucket);

	/**
	 * Removes the given bucket from the storage.
	 *
	 * @param bucket
	 */
	public void deleteBucket(Bucket bucket);
}
