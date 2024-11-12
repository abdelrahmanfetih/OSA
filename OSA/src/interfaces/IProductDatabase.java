package interfaces;
import dbadapter.productDatabase;
import java.util.*;


/**
 * Interface represents the Product component .
 *  
 *  */
public interface IProductDatabase {
	
	/**
	 * fetches a list of all the products in the DB .
	 * 
	 * @return a list of all products ordered by the price descendingly. 
	 *  */
	public List<productDatabase> getProducts(); 
	
	/**
	 * fetch a product with a given ID .
	 * 
	 * @return a product from the given ID
	 *  */
	public productDatabase getProductByID (int id); 
	
	/**
	 * check all the products that have  0 quantatity and removes them from the shopping cart and from the proudct table
	 * 	 * 
	 *  */
	public void noStock();
	
}
