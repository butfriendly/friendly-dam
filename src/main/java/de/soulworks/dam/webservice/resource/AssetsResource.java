package de.soulworks.dam.webservice.resource;

import de.soulworks.dam.domain.Asset;
import de.soulworks.dam.webservice.service.AssetService;
import com.sun.jersey.api.JResponse;
import com.sun.jersey.api.core.ResourceContext;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@Path("assets")
@Controller
@Scope("singleton")
public class AssetsResource {
	@Autowired
	private AssetService assetService;

	public AssetsResource() {
		// Required by Jersey
	}

	@GET
	@Produces({MediaType.APPLICATION_XML})
	public JResponse<List<Asset>> getAssets() {
		// Retrieve all assets
		List<Asset> assets = assetService.getAssets();

		// Return response with assets as payload
		return JResponse.ok(assets).build();
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void uploadAsset() {
		return;
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public void importAsset(JAXBElement<Asset> element) {
		// @todo Implement import of assets
		Asset a = element.getValue();

		// Check wether an asset with the same uid exists
		Asset asset = assetService.getAsset(a.getUid());
		if (asset != null) {
			throw new WebApplicationException(Response
					.status(Response.Status.CONFLICT)
					.build());
		}

		// Create asset
		assetService.createAsset(a);
	}

	@Path("{assetid}")
	public AssetResource getAssetRessource(@Context ResourceContext resourceContext,
	                                        @PathParam("assetid") String id) {
		// Retrieve the requested asset
		Asset asset = assetService.getAsset(id);

		// Did we find an asset?
		if(null == asset) {
			throw new WebApplicationException(Response
					.status(Response.Status.NOT_FOUND)
					.entity("The requested asset could not be found: " + id)
					.build());
		}

		AssetResource resource = resourceContext.getResource(AssetResource.class);
		resource.setAsset(asset);
		return resource;
	}

}
