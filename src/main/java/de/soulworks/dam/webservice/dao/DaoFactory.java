package de.soulworks.dam.webservice.dao;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
public abstract class DaoFactory {
	public static final int EKTORP   = 1;
	public static final int MOCK     = 2;
	public static final int JCOUCHDB = 3;

	public abstract AssetDao getAssetDao();
	public abstract BucketDao getBucketDao();
	public abstract CustomerDao getCustomerDao();

	public DaoFactory getDaoFactory(int whichFactory) {
		switch(whichFactory) {
			case EKTORP:
				return new EktorpDaoFactory();

//			case MOCK:
//				return new MockDaoFactory();

//			case JCOUCHDB:
//				return new JcouchdbDaoFactory();

			default:
				return null;
		}
	}
}
