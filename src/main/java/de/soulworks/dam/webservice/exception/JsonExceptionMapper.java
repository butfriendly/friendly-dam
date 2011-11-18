package de.soulworks.dam.webservice.exception;

import com.sun.jersey.core.spi.factory.ResponseBuilderImpl;
import org.apache.log4j.Logger;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JsonExceptionMapper implements ExceptionMapper<Throwable> {

	private static final Logger log = Logger.getLogger(JsonExceptionMapper.class);

	public Response toResponse(Throwable throwable) {
		// erstmal loggen
		log.error("got an throwable! ", throwable);

		// Setup the ExceptionWrapper
		ExceptionWrapper wrapper = ExceptionWrapper.createFromThrowable(throwable);

		ResponseBuilderImpl responseBuilderImpl = new ResponseBuilderImpl();
		responseBuilderImpl.entity(wrapper);
		responseBuilderImpl.type(MediaType.APPLICATION_JSON);
		/*
		if (throwable instanceof BadRequestException) {
			responseBuilderImpl.status(Response.Status.BAD_REQUEST);
			log.info("Status: " + Response.Status.BAD_REQUEST);
		} else if (throwable instanceof LoginException) {
			responseBuilderImpl.status(Status.UNAUTHORIZED);
			log.info("Status: " + Status.UNAUTHORIZED);
		} else {
			responseBuilderImpl.status(Status.INTERNAL_SERVER_ERROR);
			log.info("Status: " + Status.INTERNAL_SERVER_ERROR);
		}*/
		responseBuilderImpl.status(Status.INTERNAL_SERVER_ERROR);
		log.info("Status: " + Status.INTERNAL_SERVER_ERROR);

		return responseBuilderImpl.build();
	}
}
