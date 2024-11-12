package testing;

import org.junit.Before;
import org.junit.Test;

import net.sourceforge.jwebunit.junit.WebTester;


public class GUISystemTest {

	private WebTester tester;
	/**
	 * Create a new WebTester object that performs the test.
	 */
	@Before
	public void prepare() {
		tester = new WebTester();
		
		//base url to the website
		tester.setBaseUrl("http://localhost:8080/OSA/");
	}
	
		
	//Test for the all products method
	@Test
		public void testBrowseProducts() {
		
		tester.beginAt("AllProductsOverview");
		
		// Check the representation of the table for an empty result
		tester.assertTablePresent("tblproducts");
		String[][] tableHeadings = { { "ID", "Name", "Desctiption", "Price", "Quantatity", "Details" } };
		tester.assertTableRowsEqual("tblproducts", 0, tableHeadings);
	}
	
	
	//Test for the all products method
	@Test
		public void testAddProductToShoppingList() {
		
		tester.beginAt("product_details?pid=1");		
		tester.assertButtonPresent("registerButton");
		
		tester.assertFormPresent("formAddProduct");
		tester.assertElementPresent("productId");
		tester.assertElementPresent("productName");
		tester.assertElementPresent("productQuant");
		tester.assertElementPresent("productPrice");
		tester.assertElementPresent("productDESC");
		
		tester.clickButton("registerButton");


	}
	
}
