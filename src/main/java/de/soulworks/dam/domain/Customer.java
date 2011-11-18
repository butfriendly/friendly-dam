package de.soulworks.dam.domain;

import javax.xml.bind.annotation.*;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement
public final class Customer extends EktorpBaseDomain {
	@XmlAttribute
	private String uid;

	@XmlElement
	private String name;

	public Customer() {
		// JAXB needs this
	}

	public static Customer create() {
		return new Customer();
	}

	public String getUid() {
		return uid;
	}

	public Customer setUid(String uid) {
		this.uid = uid;
		return this;
	}

	public String getName() {
		return name;
	}

	public Customer setName(String name) {
		this.name = name;
		return this;
	}
}
