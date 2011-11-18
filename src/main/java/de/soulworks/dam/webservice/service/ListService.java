package de.soulworks.dam.webservice.service;

import de.soulworks.dam.domain.Bucket;
import de.soulworks.dam.domain.List;
import de.soulworks.dam.domain.validation.Validateable;
import de.soulworks.dam.domain.validation.ValidationException;
import de.soulworks.dam.domain.validation.validator.compound.ListValidator;
import de.soulworks.dam.webservice.dao.BucketDao;
import de.soulworks.dam.webservice.dao.ListDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@Service
public class ListService {
	@Autowired
	@Qualifier("listDao")
	private ListDao listDao;

	@Autowired
	@Qualifier("bucketDao")
	private BucketDao bucketDao;

	public List getList(String listUid) {
		return listDao.getList(listUid);
	}

	public java.util.List<List> getLists() {
		return listDao.getLists();
	}

	public java.util.List<List> getListsOfBucket(String bucketUid) {
		Bucket bucket = bucketDao.getBucket(bucketUid);

		if (null == bucket) {
			throw new IllegalArgumentException("Bucket not found");
		}

		return getListsOfBucket(bucket);
	}

	public java.util.List<List> getListsOfBucket(Bucket bucket) {
		return listDao.getListsOfBucket(bucket.getUid());
	}

	public void createList(List list) {
		try {
			// Validate the list
			validateList(list);

			// Update list at the datastore
			listDao.createList(list);
		} catch(ValidationException e) {
		}
	}

	public void updateList(String uid, List list) {
		// Lookup the list
		List l = listDao.getList(uid);

		if (null == l) {
			throw new IllegalArgumentException("The list does not exist.");
		}

		try {
			// Validate the list
			validateList(list);

			// Bind data
			l.setNames(list.getNames());
			l.setDescriptions(list.getDescriptions());

			// Update list at the datastore
			listDao.updateList(l);
		} catch(ValidationException e) {
		}
	}

	public void deleteList(List list) {
		listDao.deleteList(list);
	}

	protected void validateList(List list) {
		// Create a validatable
		Validateable<List> listValidateable = Validateable.create(list);

		// Create a validator and validate the validatable
		ListValidator validator = new ListValidator();
		validator.validate(listValidateable);

		// Throw exception if the list is invalid
		if(!listValidateable.isValid()) {
			throw new ValidationException(listValidateable.getErrors());
		}
	}
}
