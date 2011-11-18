package de.soulworks.dam.webservice.dao.mock;

import de.soulworks.dam.domain.Bucket;
import de.soulworks.dam.webservice.dao.BucketDao;

import java.util.*;

import org.springframework.stereotype.Repository;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@Repository
public class MockBucketDao implements BucketDao {

	private Map<String, Bucket> buckets = new HashMap<String, Bucket>();

	public MockBucketDao() {
		Bucket b;

		b = Bucket.create().setUid("com.example.bucket1")
				.setName("My Bucket", Locale.ENGLISH)
				.setName("Mein Bucket", Locale.GERMAN)
				.setDescription("My Bucket", Locale.ENGLISH)
				.setDescription("Mein Bucket", Locale.GERMAN);
		buckets.put("com.example.bucket1", b);

		b = Bucket.create().setUid("com.example.bucket2")
				.setName("Your Bucket", Locale.ENGLISH)
				.setName("Ihr Bucket", Locale.GERMAN)
				.setDescription("Your Bucket", Locale.ENGLISH)
				.setDescription("Ihr Bucket", Locale.GERMAN);
		buckets.put("com.example.bucket2", b);
	}

	public List<Bucket> getBuckets() {
		List<Bucket> buckets = new ArrayList<Bucket>(this.buckets.size());
		Iterator it = this.buckets.keySet().iterator();
		while(it.hasNext()) {
			buckets.add(this.buckets.get(it.next()));
		}
		return buckets;
	}

	public Bucket getBucket(String uid) {
		return buckets.get(uid);
	}

	public void createBucket(Bucket bucket) {
		buckets.put(bucket.getUid(), bucket);
	}

	public void updateBucket(Bucket bucket) {
		/*
		Bucket b = getBucket(uid);
		if(b != null) {
			b.setNames(bucket.getNames());
			b.setDescriptions(bucket.getDescriptions());
		}
		*/
	}

	public void deleteBucket(Bucket bucket) {
		buckets.remove(bucket.getUid());
	}

}
