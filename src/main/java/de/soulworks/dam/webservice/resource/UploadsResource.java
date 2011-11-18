package de.soulworks.dam.webservice.resource;

import de.soulworks.dam.domain.Upload;
import de.soulworks.dam.webservice.service.UploadService;
import com.sun.jersey.api.JResponse;
import com.sun.jersey.api.core.ResourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@Path("uploads")
@Controller
@Scope("singleton")
public class UploadsResource {
	@Autowired
	private UploadService uploadService;

	@GET
	@Produces({MediaType.APPLICATION_XML})
	public JResponse<List<Upload>> getUploads() {
		List<Upload> uploads = uploadService.getUploads();
		return JResponse.ok(uploads).build();
	}

	/**
	 * This method acts as sub-resource locator for a single upload.
	 * 
	 * @param resourceContext
	 * @param id
	 * @return
	 */
	@Path("{uploadId}")
	public UploadResource getUploadResource(@Context ResourceContext resourceContext, @PathParam("uploadId") String id) {
		// Retrieve the requested upload
		Upload upload = uploadService.getUpload(id);

		// Did we find an upload?
		if(null == upload) {
			throw new WebApplicationException(Response
					.status(Response.Status.NOT_FOUND)
					.entity("The requested upload could not be found: " + id)
					.build());
		}

		// Create resource
		UploadResource resource = resourceContext.getResource(UploadResource.class);
		resource.setUpload(upload);
		return resource;
	}
}
