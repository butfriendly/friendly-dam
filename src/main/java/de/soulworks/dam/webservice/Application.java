package de.soulworks.dam.webservice;

import de.soulworks.dam.webservice.resource.*;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
public class Application extends javax.ws.rs.core.Application {
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	/**
	 * Returns a list of classes you want to deploy into the JAX-RS environment.
	 * @return
	 */
	public Set<Class<?>> getClasses() {
		Set<Class<?>> s = new HashSet<Class<?>>();

		// Add the ressources
		s.add(BucketsResource.class);
		s.add(BucketResource.class);
		s.add(AssetsResource.class);
		s.add(AssetResource.class);
		s.add(CustomersResource.class);
		s.add(CustomerResource.class);
		s.add(ListsResource.class);

		// Add MessageBodyWriter
//		s.add(XmlMessageBodyWriter.class);
//		s.add(JsonMessageBodyWriter.class);

		// Use jackson for de-/serialization
//		s.add(JacksonJsonProvider.class);

		// Add exception-stuff
//		s.add(ExceptionWrapper.class);
//		s.add(JsonExceptionMapper.class);
//		s.add(XmlExceptionMapper.class);

		return s;
	}

	@Override
	public Set<Object> getSingletons() {
		Set<Object> s = new HashSet<Object>();

		// Register the Jackson provider for JSON

		// Make (de)serializer use a subset of JAXB and (afterwards) Jackson annotations
		// See http://wiki.fasterxml.com/JacksonJAXBAnnotations for more information
		ObjectMapper mapper = new ObjectMapper();
		AnnotationIntrospector primary   = new JaxbAnnotationIntrospector();
		AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
		AnnotationIntrospector pair      = new AnnotationIntrospector.Pair(primary, secondary);

		DeserializationConfig deserializationConfig = mapper.getDeserializationConfig();
		deserializationConfig.setAnnotationIntrospector(pair);
		deserializationConfig.set(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		SerializationConfig serializationConfig = mapper.getSerializationConfig();
		serializationConfig.setAnnotationIntrospector(pair);
		serializationConfig.set(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, true);

		// Set up the provider
		JacksonJaxbJsonProvider jaxbProvider = new JacksonJaxbJsonProvider();
		jaxbProvider.setMapper(mapper);

		s.add(jaxbProvider);

		return s;
	}

}
