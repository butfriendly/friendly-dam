package de.soulworks.dam.domain;

import de.soulworks.dam.domain.adapter.LocalizedMapAdapter;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement
public final class Asset extends EktorpBaseDomain implements Cloneable {
	@XmlAttribute(name = "uid")
	private String uid;

	@XmlTransient
	private String bucketUid;

	@XmlJavaTypeAdapter(LocalizedMapAdapter.class)
	@XmlElement(name = "title")
	private Map<Locale, String> title = new HashMap<Locale, String>();

	@XmlJavaTypeAdapter(LocalizedMapAdapter.class)
	@XmlElement(name = "description")
	private Map<Locale, String> description = new HashMap<Locale, String>();

//	@JsonProperty("type")
	@XmlAttribute(name = "type")
	private AssetType type;

//	@JsonProperty("media_id")
	@XmlTransient
	private long mediaId;

//	@JsonProperty("originator_uid")
	@XmlAttribute(name = "originator_uid")
	private String originatorUid;

	public Asset() { // JAXB needs this
	}

	public Asset(String uid) {
		this.uid = uid;
	}

	public static Asset create() {
		return new Asset();
	}

	public long getMediaId() {
		return mediaId;
	}

	public Asset setMediaId(long mediaId) {
		this.mediaId = mediaId;
		return this;
	}

	public String getOriginatorUid() {
		return originatorUid;
	}

	public Asset setOriginatorUid(String originatorUid) {
		this.originatorUid = originatorUid;
		return this;
	}

	public Asset setOriginator(Customer customer) {
		String customerUid = customer.getUid();

		if(null == customerUid) {
			throw new IllegalArgumentException("The customer doesn't have any uid.");
		}

		return setOriginatorUid(customerUid);
	}

	public Map<Locale, String> getTitles() {
		return title;
	}

	public Asset setTitles(Map<Locale, String> title) {
		this.title = title;
		return this;
	}

	/**
	 * Returns the title of the asset.
	 *
	 * @return String Title of the asset
	 */
	@JsonIgnore
	public String getTitle(Locale locale) {
		return title.get(locale);
	}

	/**
	 * Sets the title of the asset at the given locale.
	 *
	 * @param title  Title of the asset
	 * @param locale Locale of the title's language
	 * @return Asset The asset itself
	 */
	public Asset setTitle(String title, Locale locale) {
		this.title.put(locale, title);
		return this;
	}

	public Map<Locale, String> getDescriptions() {
		return description;
	}

	public Asset setDescriptions(Map<Locale, String> description) {
		this.description = description;
		return this;
	}

	/**
	 * Returns the description of the asset.
	 *
	 * @return String Description of the asset
	 */
	@JsonIgnore
	public String getDescription(Locale locale) {
		return description.get(locale);
	}

	/**
	 * Sets the description of the asset.
	 *
	 * @param description Description of the asset
	 * @param locale      Locale of the description's locale
	 * @return Asset The asset itself
	 */
	public Asset setDescription(String description, Locale locale) {
		this.description.put(locale, description);
		return this;
	}

	/**
	 * Returns the uid of the bucket the asset is in
	 *
	 * @return String Uid of the bucket
	 */
	public String getBucketUid() {
		return bucketUid;
	}

	/**
	 * Set the uid of the bucket the asset is in
	 *
	 * @param bucketUid Uid of a bucket
	 * @return Asset The asset itself
	 */
	public Asset setBucketUid(String bucketUid) {
		this.bucketUid = bucketUid;
		return this;
	}

	public Asset setBucket(Bucket bucket) {
		String bucketUid = bucket.getUid();

		if (null == bucketUid) {
			throw new IllegalArgumentException("The bucket don't have any uid.");
		}

		return setBucketUid(bucketUid);
	}

	/**
	 * Returns the type of the asset
	 *
	 * @return
	 */
	public AssetType getType() {
		return type;
	}

	/**
	 * Sets the type of the asset
	 */
	public Asset setType(AssetType type) {
		this.type = type;
		return this;
	}

	/**
	 * Returns the unique identifier of the asset.
	 *
	 * @return
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * Sets the unique identifier of the asset.
	 *
	 * @param uid
	 */
	public Asset setUid(String uid) {
		this.uid = uid;
		return this;
	}

	@Override
	public String toString() {
		return getUid();
	}

	@Override
	public Asset clone() {
		try {
			Asset asset = (Asset)super.clone();

			HashMap<Locale, String> titles = new HashMap<Locale, String>(getTitles());
			asset.setTitles(titles);

			HashMap<Locale, String> descriptions = new HashMap<Locale, String>(getDescriptions());
			asset.setDescriptions(descriptions);

			return asset;
		}
		catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError();
		}
	}
}
