package dbadapter;

/**
 * Class represents the entity product in the database with all its properties
 *  
 *  */
public class productDatabase {
	
	private int ProductID; 
	
	private float price; 
	
	
	private String Name; 
	
	private int ProductQuantatity; 
	
	private String  Description;
	
	
	public productDatabase ( ) {
		
	}
	
	public productDatabase (int productID, String name , String description, float price, int quantatiy) { 
		
		this.Name = name; 
		this.ProductQuantatity = quantatiy;
		this.Description = description; 
		this.price = price; 
		this.ProductID = productID;
	}

	public String getProductDescription() {
		return Description;
	}

	public void setProductDescription(String productDetails) {
		Description = productDetails;
	}

	public int getProductQuantatity() {
		return ProductQuantatity;
	}

	public void setProductQuantatity(int productQuantatity) {
		ProductQuantatity = productQuantatity;
	}

	public String getProductName() {
		return Name;
	}

	public void setProductName(String productName) {
		Name = productName;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getProductID() {
		return ProductID;
	}

	public void setProductID(int productID) {
		ProductID = productID;
	} 
	
	
	

}
