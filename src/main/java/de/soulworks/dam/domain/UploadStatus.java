package de.soulworks.dam.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@XmlEnum
public enum UploadStatus {
	@XmlEnumValue("pending")
	PENDING("p"),

	@XmlEnumValue("complete")
	COMPLETE("c");

	private final String value;

	UploadStatus(String v) {
		value = v;
	}
}
