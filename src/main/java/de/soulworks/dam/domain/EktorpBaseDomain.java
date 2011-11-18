package de.soulworks.dam.domain;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.CouchDbDocument;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.lang.annotation.Annotation;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@XmlTransient
abstract public class EktorpBaseDomain extends CouchDbDocument {
	private String documentType;
	private boolean isDeleted = false;

	@JsonProperty("_deleted")
	public boolean isDeleted() {
		return isDeleted;
	}

	@JsonProperty("_deleted")
	public void setDeleted(boolean deleted) {
		this.isDeleted = deleted;
	}

	public EktorpBaseDomain() {
		super();
		documentType = this.getClass().getSimpleName();
	}

	@JsonProperty(value = "documentType")
	public String getDocumentType() {
		return documentType;
	}

	@JsonProperty(value = "documentType")
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	@Override
	@XmlTransient
	public String getId() {
		return super.getId();
	}

	@Override
	@XmlTransient
	public String getRevision() {
		return super.getRevision();
	}

	@Override
	public boolean equals(Object subject) {
		if (subject instanceof CouchDbDocument) {
			CouchDbDocument that = (CouchDbDocument) subject;
			return this.getId().equals(that.getId()) && this.getRevision().equals(that.getRevision());
		}
		return false;
	}
}
