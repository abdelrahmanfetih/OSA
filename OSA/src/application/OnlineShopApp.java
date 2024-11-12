package application;

import java.sql.SQLException;
import java.util.List;
import dbadapter.DBFacade;
import dbadapter.UserDatabase;
import dbadapter.productDatabase;
import interfaces.CCmds;
import interfaces.RCCmds;

/**
 * This class represents the machine that interacts with the environment.
 * 
 * 
 */
public class OnlineShopApp implements RCCmds, CCmds {

	private static OnlineShopApp instance;

	private String LoggedUserName = null;

	/**
	 * Implementation of the Singleton pattern.
	 * 
	 * @return instance of the class
	 */
	public static OnlineShopApp getInstance() {

		if (instance == null) {
			instance = new OnlineShopApp();
		}

		return instance;
	}

	/**
	 * This function retunrs the current logged in user, if no one is logged in ->
	 * No Loggedin user will .
	 * 
	 * @return Current logged in user -> no logged in -> No loggedin user will be
	 *         returned
	 */

	public String getLoggedUserName() {

		if (this.LoggedUserName == null)
			return "No Loggedin user";
		else
			return this.LoggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		LoggedUserName = loggedUserName;
	}

	/**
	 * This function implements the method in the interface CCmds .
	 * 
	 * @return true if the regestration is correcct -> false other wise
	 */
	@Override
	public Boolean registerUser(String username, String password) {

		UserDatabase user = new UserDatabase(username.trim(), password.trim());

		return DBFacade.getInstance().registeringUser(user);
	}

	/**
	 * List of all products that the user can see .
	 * 
	 * @return List of all the product that the registerd user can see
	 */
	@Override
	public List<productDatabase> forwardBrowseProducts() {
		noStock();
		return DBFacade.getInstance().getProducts();
	}

	/*
	 * Adds a product to the shopping cart of a specific user
	 * 
	 * @return true if the adding was successful and fasle otherwise.
	 */
	@Override
	public Boolean forwardAddProductToCart(String username, int productID) throws SQLException {

		try {
			return DBFacade.getInstance().addingProduct(username, productID);
		} catch (SQLException e) {
			throw e;
		}
	}
	
	
	/**
	 * fetch a product with a given ID .
	 * 
	 * @return a product from the given ID
	 *  */
	@Override
	public productDatabase getProductByID(int id) {

		return DBFacade.getInstance().getProductByID(id);
	}
	
	/**
	 * check all the products that have  0 quantatity and removes them from the shopping cart and from the proudct table
	 * 	 * 
	 *  */
	@Override
	public void noStock() {
		DBFacade.getInstance().noStock();
	}

}
