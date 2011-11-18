package de.soulworks.dam.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@XmlEnum
public enum AssetType {
	@XmlEnumValue("video")
	VIDEO("video"),

	@XmlEnumValue("audio")
	AUDIO("audio"),

	@XmlEnumValue("image")
	IMAGE("image");

	private final String value;

	AssetType(String v) {
		value = v;
	}
}
