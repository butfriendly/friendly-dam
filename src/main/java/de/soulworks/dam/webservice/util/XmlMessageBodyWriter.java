package de.soulworks.dam.webservice.util;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * A MessageBodyWriter which uses XmlSimple
 */
@Provider
@Produces(MediaType.APPLICATION_XML)
public class XmlMessageBodyWriter implements MessageBodyWriter<Object> {
	private static final Logger log = LoggerFactory.getLogger(XmlMessageBodyWriter.class);

	public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
		return mediaType.isCompatible(MediaType.APPLICATION_XML_TYPE);
	}

	public long getSize(Object o, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	public void writeTo(Object o, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType,
		MultivaluedMap<String, Object> stringObjectMultivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
		
		log.info("Try to serialize: "+aClass.getSimpleName());

		Serializer serializer = new Persister();

		try {
			serializer.write(o, outputStream);
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}
}
