package de.soulworks.dam.domain;

import javax.xml.bind.annotation.*;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement
public final class Account extends EktorpBaseDomain {
	@XmlAttribute(name = "customer_uid")
	private String customerUid;

	@XmlElement
	private String username;

	private String passwordHash;

	@XmlAttribute(name = "is_expired")
	private boolean isExpired = false;

	@XmlAttribute(name = "is_locked")
	private boolean isLocked = false;

	public Account() {
		// JAXB needs this
	}

	public static Account create() {
		return new Account();
	}

	/**
	 * Returns the username of the account
	 * @return String Username for the account
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets username of the account
	 * @param username Username for the account
	 * @return Account The account itself
	 */	
	public Account setUsername(String username) {
		this.username = username;
		return this;
	}

	/**
	 * Returns password-hash of the account
	 * @return String Password-hash of the account
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Sets password-hash of the account
	 * @param passwordHash Password-hash of the account
	 * @return Account The account itself
	 */
	public Account setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
		return this;
	}

	/**
	 * Returns wether the account is expired or not
	 * @return boolean Status of expiration
	 */
	public boolean isExpired() {
		return isExpired;
	}

	/**
	 * Sets expiration for the account
	 * @param expired Status of expiration
	 * @return Account The account itself
	 */
	public Account setExpired(boolean expired) {
		isExpired = expired;
		return this;
	}

	/**
	 * Returns wether the account is locked or not
	 * @return boolean Status of lock
	 */
	public boolean isLocked() {
		return isLocked;
	}

	/**
	 * Sets lock for the account
	 * @param locked Status of lock
	 * @return Account The account itself
	 */
	public Account setLocked(boolean locked) {
		isLocked = locked;
		return this;
	}

	/**
	 * Returns identifier of the assoziated customer
	 * @return String Identifier of the assoziated customer
	 */
	public String getCustomerUid() {
		return customerUid;
	}

	/**
	 * Set the identifier of the assozaited customer
	 * @param customerUid Identifier of the assoziated customer
	 * @return Account The account itself
	 */
	public Account setCustomerUid(String customerUid) {
		this.customerUid = customerUid;
		return this;
	}
}
