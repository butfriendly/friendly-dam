package de.soulworks.dam.webservice.resource;

import de.soulworks.dam.domain.Upload;
import de.soulworks.dam.webservice.service.UploadService;
import com.sun.jersey.api.JResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@Controller
@Scope("prototype")
public class UploadResource {
	private Upload upload;

	@Autowired
	private UploadService uploadService;

	public Upload getUpload() {
		return upload;
	}

	public void setUpload(Upload upload) {
		this.upload = upload;
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public JResponse<Upload> getDetails() {
		return JResponse.ok(getUpload()).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response updateUpload(JAXBElement<Upload> element) {
		// Extract the upload
		Upload upload = element.getValue();

		// Update the current upload with data of the extracted upload
		uploadService.updateUpload(getUpload().getUid(), upload);

		// We are fine
		return Response.ok().build();
	}

	@DELETE
	public void deleteUpload() {
		uploadService.deleteUpload(getUpload());
	}
}
