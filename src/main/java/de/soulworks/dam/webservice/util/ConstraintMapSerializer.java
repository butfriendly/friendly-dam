package de.soulworks.dam.webservice.util;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
public class ConstraintMapSerializer extends JsonSerializer<SortedMap<Date,String>> {
	@Override
	public void serialize(SortedMap<Date, String> value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();

//		mapper.writeValue(jgen, values);

		jgen.writeStartObject();
		for(Date key: value.keySet()) {
			SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
			String formattedDate = formatter.format(key);

			jgen.writeFieldName(formattedDate);
			jgen.writeString(value.get(key));
		}
		jgen.writeEndObject();
	}
}
