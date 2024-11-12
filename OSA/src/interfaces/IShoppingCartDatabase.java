package interfaces;

public interface IShoppingCartDatabase {

	/*
	 * Adds a product to the shopping cart of a specific user
	 * @return true if the adding was successful and fasle otherwise. 
	 * */
	public Boolean addingProduct(String username, int productID) throws Exception;

}
