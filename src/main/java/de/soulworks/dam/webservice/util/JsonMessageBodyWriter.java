package de.soulworks.dam.webservice.util;

import com.google.gson.Gson;

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
/*
import com.google.gson.Gson;
import com.sun.jersey.spi.resource.Singleton;

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
*/

/**
 * @author christian.schmitz
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JsonMessageBodyWriter implements MessageBodyWriter<Object> {

	public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
//		aClass.isAnnotationPresent(JsonMessageBodyWriter)
		return mediaType.isCompatible(MediaType.APPLICATION_JSON_TYPE);
	}

	public long getSize(Object o, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	public void writeTo(Object o, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> stringObjectMultivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
		
		Gson gson = new Gson();
		outputStream.write(gson.toJson(o, type).getBytes("UTF-8"));
	}
}
