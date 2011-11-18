package de.soulworks.dam.domain.adapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.*;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
public class ConstraintsMapAdapter extends XmlAdapter<ConstraintsMapAdapter.Constraints, SortedMap<String, String>> {
	@Override
	public SortedMap<String, String> unmarshal(Constraints v) throws Exception {
		SortedMap<String, String> m = new TreeMap<String, String>();
		for (Constraint item : v.items) {
			m.put(item.validFrom, item.constraint);
		}
		return m;
	}

	@Override
	public Constraints marshal(SortedMap<String, String> v) throws Exception {
		Iterator it = v.keySet().iterator();
		Constraints constraints = new Constraints();
		while (it.hasNext()) {
			// Get key value, which is the locale
			String validFrom = (String) it.next();

			// Build a new map-item
			Constraint constraint = new Constraint();
			constraint.validFrom  = validFrom;
			constraint.constraint = v.get(validFrom);

			// Add the item
			constraints.items.add(constraint);
		}
		return constraints;
	}

	public static class Constraint {
		@XmlAttribute
		public String validFrom;

		@XmlValue
		public String constraint;
	}

	public static class Constraints {
		@XmlElement(name = "value")
		public java.util.List<Constraint> items = new ArrayList<Constraint>();
	}
}
