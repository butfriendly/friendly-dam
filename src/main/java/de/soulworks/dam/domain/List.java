package de.soulworks.dam.domain;

import de.soulworks.dam.domain.adapter.LocalizedMapAdapter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement
public final class List extends EktorpBaseDomain {
	@XmlAttribute(name = "uid")
	private String uid;

	@XmlAttribute(name = "bucket_uid")
	@JsonProperty(value = "bucket_uid")
	private String bucketUid;

	@XmlJavaTypeAdapter(LocalizedMapAdapter.class)
	@XmlElement(name = "name")
	private Map<Locale, String> name = new HashMap<Locale, String>();

	@XmlJavaTypeAdapter(LocalizedMapAdapter.class)
	@XmlElement(name = "description")
	private Map<Locale, String> description = new HashMap<Locale, String>();

	public List() { // JAXB needs this
	}

	public static List create() {
		return new List();
	}

	/**
	 * Returns the unique identifier of the list.
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * Returns the name of the list with it's localizations.
	 *
	 * @return
	 */
	public Map<Locale, String> getNames() {
		return name;
	}

	/**
	 * Sets the {@code name} of the list at the given {@code locale}.
	 */
	public List setName(String name, Locale locale) {
		this.name.put(locale, name);
		return this;
	}

	/**
	 * Returns the name of the list at the given {@code locale}.
	 */
	@JsonIgnore
	public String getName(Locale locale) {
		return name.get(locale);
	}

	public List setNames(Map<Locale, String> description) {
		this.name = name;
		return this;
	}

	/**
	 * Returns the description and it's localizations.
	 *
	 * @return
	 */
	public Map<Locale, String> getDescriptions() {
		return description;
	}

	public List setDescriptions(Map<Locale, String> description) {
		this.description = description;
		return this;
	}

	/**
	 * Sets the {@code description} of the list at the given {@code locale}.
	 */
	public List setDescription(String description, Locale locale) {
		this.description.put(locale, description);
		return this;
	}

	/**
	 * Returns the description of the list at the given {@code locale}.
	 */
	@JsonIgnore
	public String getDescription(Locale locale) {
		return description.get(locale);
	}

	public String getBucketUid() {
		return bucketUid;
	}

	public List setBucketUid(String bucketUid) {
		this.bucketUid = bucketUid;
		return this;
	}

}
