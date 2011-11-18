package de.soulworks.dam.webservice.resource;

import de.soulworks.dam.domain.Bucket;
import de.soulworks.dam.domain.Asset;
import de.soulworks.dam.domain.Upload;
import de.soulworks.dam.webservice.service.BucketService;
import de.soulworks.dam.webservice.service.AssetService;

import java.net.URI;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import de.soulworks.dam.webservice.service.UploadService;
import com.sun.jersey.api.JResponse;
import com.sun.jersey.api.core.ResourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@Controller
@Scope("prototype")
public class BucketResource {
	private Bucket bucket;

	@Autowired
	private BucketService bucketService;

	@Autowired
	private AssetService assetService;

	@Autowired
	private UploadService uploadService;
	
	@Context
	private UriInfo uriInfo;

	public Bucket getBucket() {
		return bucket;
	}

	public void setBucket(Bucket bucket) {
		this.bucket = bucket;
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public JResponse<Bucket> getBucketDetails() {
		return JResponse.ok(getBucket()).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response updateBucket(JAXBElement<Bucket> element) {
		// Extract the bucket
		Bucket bucket = element.getValue();

		// Update the current bucket with data of the extracted bucket
		bucketService.updateBucket(getBucket().getUid(), bucket);

		// We are fine
		return Response.ok().build();
	}

	@DELETE
	public void deleteBucket() {
		bucketService.deleteBucket(getBucket());
	}

	@POST
	@Path("/uploads")
	@Consumes(MediaType.APPLICATION_XML)
	public JResponse<Upload> getUploadId() {
		Upload upload = uploadService.createUniqueUpload()
				.setBucketUid(getBucket().getUid());

		uploadService.createUpload(upload);

		return JResponse.ok(upload).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/uploads")
	public JResponse<List<Upload>> getUploadsOfBucket() {
		List<Upload> uploads = uploadService.getUploadsOfBucket(getBucket());
		return JResponse.ok(uploads).build();
	}

	@GET
	@Path("/assets")
	public JResponse<List<Asset>> getAssetsOfBucket() {
		return JResponse.ok(assetService.getAssetsOfBucket(getBucket().getUid())).build();
	}

	/**
	 * Import the given asset to the current bucket.
	 * 
	 * @param element
	 */
	@POST
	@Path("/assets")
	@Consumes(MediaType.APPLICATION_XML)
	public Response importAsset(JAXBElement<Asset> element) {
		// Extract asset from the request
		Asset asset = element.getValue();

		// Import the asset
		Asset importedAsset = bucketService.importAssetToBucket(asset, getBucket());

		// Generate URI for the new resource
		URI uri = uriInfo.getAbsolutePathBuilder().path(importedAsset.getUid()).build();

		return Response.created(uri).build();
	}

	@Path("/lists")
	public ListsResource getListsResource(@Context ResourceContext resourceContext) {
		ListsResource resource = resourceContext.getResource(ListsResource.class);
		resource.setBucket(getBucket());
		return resource;
	}

}
