package de.soulworks.dam.webservice.resource;

import de.soulworks.dam.domain.Asset;
import de.soulworks.dam.webservice.service.AssetService;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

import com.sun.jersey.api.JResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@Controller
@Scope("prototype")
public class AssetResource {
	private Asset asset;

	@Autowired
	private AssetService assetService;

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public Asset getAsset() {
		return this.asset;
	}

	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public JResponse<Asset> getAssetDetails() {
		return JResponse.ok(getAsset()).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public void updateAsset(JAXBElement<Asset> element) {
		Asset asset = element.getValue();
		
		assetService.updateAsset(getAsset().getUid(), asset);
	}

	@DELETE
	public void deleteAsset() {
		assetService.deleteAsset(getAsset());
	}
}
