package de.soulworks.dam.webservice.util;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
public class ConstraintMapDeserializer extends JsonDeserializer<SortedMap<Date, String>> {
	private static final Logger log = LoggerFactory.getLogger(ConstraintMapDeserializer.class);

	@Override
	public SortedMap<Date, String> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		SortedMap<Date, String> constraints = new TreeMap<Date, String>();

		TypeReference<SortedMap<Date, String>> typeRef = new TypeReference<SortedMap<Date, String>>() {};

		ObjectMapper mapper = new ObjectMapper();
		SortedMap<String, String>[] maps = mapper.readValue(jp, typeRef);

		for (SortedMap<String, String> constraint : maps) {
			log.info("des called");
			constraint.get("name");
			constraint.get("value");
		}

		return constraints;
	}
}
