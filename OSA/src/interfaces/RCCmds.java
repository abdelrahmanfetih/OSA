package interfaces;

import java.util.List;

import dbadapter.productDatabase;

public interface RCCmds {
	
	public List<productDatabase> forwardBrowseProducts();
	
	/*
	 * Adds a product to the shopping cart of a specific user
	 * @return true if the adding was successful and fasle otherwise. 
	 * */
	public Boolean forwardAddProductToCart(String username, int productID) throws Exception ;
	
	/*
	 * gets the product from a given productID
	 * @return true if the adding was successful and fasle otherwise. 
	 * */
	public productDatabase getProductByID (int id); 
	
	/*
	 * returns a list of products that should be deleted becuase the quantatiy is zero 
	 * @return list of products . 
	 * */
	public void noStock ();
	
	}
	
