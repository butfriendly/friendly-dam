package de.soulworks.dam.webservice.resource;

import de.soulworks.dam.domain.Bucket;
import de.soulworks.dam.webservice.dao.AssetDao;
import de.soulworks.dam.webservice.service.BucketService;
import com.sun.jersey.api.JResponse;
import com.sun.jersey.api.core.ResourceContext;

import java.net.URI;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@Path("buckets")
@Controller
@Scope("singleton")
public class BucketsResource {
	private static final Logger log = LoggerFactory.getLogger(BucketsResource.class);

	@Context
	private UriInfo uriInfo;

	@Autowired
	private BucketService bucketService;

	public BucketsResource() {
		// Required by Jersey
	}

	@Autowired
	@Qualifier("assetDao")
	private AssetDao assetDao;

	@GET
	@Produces({MediaType.APPLICATION_XML})
	public JResponse<List<Bucket>> getBuckets() {
		// Retrieve buckets
		List<Bucket> buckets = bucketService.getBuckets();

		// Return response
		return JResponse.ok(buckets).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response createBucket(JAXBElement<Bucket> element) {
		// @todo Add validation
		Bucket bucket = element.getValue();

		// Create the bucket
		bucketService.createBucket(bucket);

		// Generate URI for the new resource
		URI uri = uriInfo.getAbsolutePathBuilder().path(bucket.getUid()).build();

		return Response.created(uri).build();
	}
	
	@Path("{bucketId}")
	public BucketResource getBucketResource(@Context ResourceContext resourceContext, @PathParam("bucketId") String id) {
		// Retrieve the requested bucket
		Bucket bucket = bucketService.getBucket(id);

		// Did we find a bucket?
		if(null == bucket) {
			throw new WebApplicationException(Response
					.status(Response.Status.NOT_FOUND)
					.entity("The requested bucket could not be found: " + id)
					.build());
		}

		// Create resource
		BucketResource resource = resourceContext.getResource(BucketResource.class);
		resource.setBucket(bucket);
		return resource;
	}
}
