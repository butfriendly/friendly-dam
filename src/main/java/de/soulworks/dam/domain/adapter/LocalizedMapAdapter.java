package de.soulworks.dam.domain.adapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.*;

/**
 * Implementation of a {@link XmlAdapter} for (un-)marshalling of hash-maps, which are used for
 * i18n of domain-properties.
 * 
 * @author Christian Schmitz <csc@soulworks.de>
 */
public final class LocalizedMapAdapter extends XmlAdapter<LocalizedMapAdapter.LocalizedMap, Map<Locale, String>> {
	public static class LocalizedMapItem {
		@XmlAttribute
		public String language;

		@XmlValue
		public String value;
	}

	public static class LocalizedMap {
		@XmlElement(name = "item")
		public java.util.List<LocalizedMapItem> items = new ArrayList<LocalizedMapItem>();
	}

	@Override
	public Map<Locale, String> unmarshal(LocalizedMap v) throws Exception {
		Map<Locale, String> m = new HashMap<Locale, String>();
		for (LocalizedMapItem item : v.items) {
			Locale locale = new Locale(item.language);
			m.put(locale, item.value);
		}
		return m;
	}

	@Override
	public LocalizedMap marshal(Map<Locale, String> v) throws Exception {
		Iterator it = v.keySet().iterator();
		LocalizedMap lm = new LocalizedMap();
		while (it.hasNext()) {
			// Get key value, which is the locale
			Locale locale = (Locale) it.next();

			// Build a new map-item
			LocalizedMapItem lmi = new LocalizedMapItem();
			lmi.language = locale.getLanguage();
			lmi.value = v.get(locale);

			// Add the item
			lm.items.add(lmi);
		}
		return lm;
	}
}
