package de.soulworks.dam.domain;

import de.soulworks.dam.domain.adapter.ConstraintsMapAdapter;
import de.soulworks.dam.domain.adapter.LocalizedMapAdapter;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement
public final class Bucket extends EktorpBaseDomain {
	@XmlAttribute(name = "uid")
	private String uid;

	@XmlAttribute(name = "customer_uid")
	private String customerUid;

	@XmlJavaTypeAdapter(LocalizedMapAdapter.class)
	@XmlElement(name = "name")
	private Map<Locale, String> name = new HashMap<Locale, String>();

	@XmlJavaTypeAdapter(LocalizedMapAdapter.class)
	@XmlElement(name = "description")
	private Map<Locale, String> description = new HashMap<Locale, String>();

	@XmlJavaTypeAdapter(ConstraintsMapAdapter.class)
	@XmlElement(name = "constraints")
	private SortedMap<String, String> constraints = new TreeMap<String, String>();

	@XmlElementWrapper(name="profiles")
	@XmlElement(name="profile")
	private java.util.List<String> profiles;

	public Bucket() {
		// JAXB needs this
		super();
	}

	public static Bucket create() {
		return new Bucket();
	}

	public Bucket(String uid) {
		this.uid = uid;
	}

	public String getUid() {
		return uid;
	}

	public Bucket setUid(String uid) {
		this.uid = uid;
		return this;
	}

	public String getCustomerUid() {
		return customerUid;
	}

	public Bucket setCustomerUid(String customerUid) {
		this.customerUid = customerUid;
		return this;
	}

	public Bucket setCustomer(Customer customer) {
		String customerUid = customer.getUid();

		if (null == customerUid) {
			throw new IllegalArgumentException("Invalid customer");
		}

		return setCustomerUid(customerUid);
	}

	public Bucket setConstraint(Date date, String constraint) {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'h:m:ssZ");
		constraints.put(sdf.format(date), constraint);
		return this;
	}

	public java.util.List<String> getProfiles() {
		return profiles;
	}

	public Bucket setProfiles(java.util.List<String> profiles) {
		this.profiles = profiles;
		return this;
	}

	@JsonIgnore
	public String getConstraint(Date date) {
		return constraints.get(date);
	}

	public SortedMap<String, String> getConstraints() {
		return constraints;
	}

	public Bucket setConstraints(SortedMap<String, String> constraints) {
		this.constraints = constraints;
		return this;
	}

	public Map<Locale, String> getNames() {
		return name;
	}

	public Bucket setNames(Map<Locale, String> name) {
		this.name = name;
		return this;
	}

	@JsonIgnore
	public String getName(Locale locale) {
		return name.get(locale);
	}

	public Bucket setName(String name, Locale locale) {
		this.name.put(locale, name);
		return this;
	}

	public Map<Locale, String> getDescriptions() {
		return description;
	}

	public Bucket setDescriptions(Map<Locale, String> description) {
		this.description = description;
		return this;
	}

	@JsonIgnore
	public String getDescription(Locale locale) {
		return description.get(locale);
	}

	public Bucket setDescription(String description, Locale locale) {
		this.description.put(locale, description);
		return this;
	}

	@Override
	public String toString() {
		return getUid();
	}

	public boolean equals(Bucket obj) {
		return this.getUid().equals(obj.getUid());
	}

}
