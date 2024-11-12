package interfaces;

import dbadapter.UserDatabase;

/**
 * Interface represents the Product USerDatabase component .
 *  
 *  */
public interface IUserDatabase {
	
	/**
	 * registers the user and save its data in the DB .
	 * @return true if the registration was successful -> false otherwise. 
	 *  */
	public Boolean registeringUser (UserDatabase userData);
	
}