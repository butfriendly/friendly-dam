package de.soulworks.dam.webservice.resource;

import de.soulworks.dam.domain.Bucket;
import de.soulworks.dam.domain.List;
import de.soulworks.dam.webservice.service.ListService;
import com.sun.jersey.api.JResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@Controller
@Scope("prototype")
public class ListsResource {
	@Autowired
	private ListService listService;
	
	private Bucket bucket;

	public Bucket getBucket() {
		return bucket;
	}

	public void setBucket(Bucket bucket) {
		this.bucket = bucket;
	}

	@GET
	@Produces({MediaType.APPLICATION_XML})
	public JResponse<java.util.List<List>> getLists() {
		return JResponse.ok(listService.getListsOfBucket(getBucket())).build();
	}
}
