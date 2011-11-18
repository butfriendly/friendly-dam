package de.soulworks.dam.webservice.dao;

import de.soulworks.dam.domain.List;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
public interface ListDao {
	public List getList(String listUid);

	public java.util.List<List> getLists();

	public java.util.List<List> getListsOfBucket(String bucketUid);

	public void createList(List list);

	public void updateList(List list);

	public void deleteList(List list);
}
