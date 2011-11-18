package de.soulworks.dam.domain;

import org.jcouchdb.document.BaseDocument;
import org.svenson.JSONProperty;

import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
class JcouchdbBaseDomain extends BaseDocument {

	/**
	 * Returns the simple name of the class as doc type.
	 * 
	 * The annotation makes it a read-only property and
	 * also shortens the JSON name a little.
	 *
	 * @return
	 */
	@JSONProperty(value = "doc_type", readOnly = true)
	public String getDocumentType() {
		return this.getClass().getSimpleName();
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

}
