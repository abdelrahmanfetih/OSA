package testing;

import dbadapter.*;
import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComponentTest extends TestCase {

	// dummy product to be inserted in the db
	private productDatabase notAddedProduct = new productDatabase();

	// dummy user to be inserted in the db.
	private UserDatabase testUserData = new UserDatabase();

	// dummy user to be inserted in the db.
	private UserDatabase testregesteredUser = new UserDatabase();

	// connection string.
	private String url = "jdbc:mysql://127.0.0.1:3306/Osa?user=root&password=" + Configuration.getPassword()
			+ "&useUnicode=true&characterEncoding=UTF-8"
			+ "&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=GMT&useSSL=false";

	public ComponentTest() {
		super();
	}

	// preparation before running the tests.
	@Before
	public void setUp() {
		// setting the dummy product data.
		this.notAddedProduct.setProductName("Iphone 13");
		this.notAddedProduct.setPrice(12);
		this.notAddedProduct.setProductDescription("red");
		this.notAddedProduct.setProductID(1);
		this.notAddedProduct.setProductQuantatity(0);

		// setting the dummy user data.
		this.testUserData.setUsername("mido");
		this.testUserData.setPassword("1234");

		this.testregesteredUser = new UserDatabase("regUser", "12345");

		// SQL statements
		String sqlCleanDB = "DROP TABLE IF EXISTS shopping, product,user";
		// String createOsaDB = "CREATE DATABASE msa";

		// insert Product statment
		String sqlInsertProductDatabaseOsa = "INSERT INTO product (productID, name, price, description, productQuantity)"
				+ " values (?,?, ?, ?, ?)";

		// insert Product statment
		String sqlInsertUser = "INSERT INTO user (username, password) values (?, ?); ";

		// insert Product statment
		String sqlInsertProductIntoShoppingCard = "INSERT INTO shopping (username, productID) values (?, ?)";

		String sqlConnect = "use Osa;";

		// Perform database updates
		try (Connection connection = DriverManager.getConnection(this.url)) {

			// cleaning and dropping the table
			try (PreparedStatement psClean = connection.prepareStatement(sqlCleanDB)) {
				psClean.executeUpdate();
			}
			// connecting to the database osa
			try (PreparedStatement psCreateDataBaseOsa = connection.prepareStatement(sqlConnect)) {
				psCreateDataBaseOsa.executeUpdate();
			}
			try {

				Connection connectionScript = DriverManager.getConnection(this.url);
				ScriptRunner runner = new ScriptRunner(connectionScript, false, false);
				// path should refer correctly to where the file is put.
				String file = "D:/proj_tot/SQL/SQLForOsa.sql";

				runner.runScript(new BufferedReader(new FileReader(file)));

			} catch (SQLException e) {
				System.err.println("Unable to connect to server: " + e);
			}
			// inserting the dummy product details
			try (PreparedStatement psInsertProduct = connection.prepareStatement(sqlInsertProductDatabaseOsa)) {
				psInsertProduct.setInt(1, notAddedProduct.getProductID());
				psInsertProduct.setString(2, notAddedProduct.getProductName());
				psInsertProduct.setFloat(3, notAddedProduct.getPrice());
				psInsertProduct.setString(4, notAddedProduct.getProductDescription());
				psInsertProduct.setInt(5, notAddedProduct.getProductQuantatity());
				psInsertProduct.executeUpdate();
			}
			// cleaning and dropping the table
			try (PreparedStatement psInsert = connection.prepareStatement(sqlInsertUser)) {
				psInsert.setString(1, this.testregesteredUser.getUsername());
				psInsert.setString(2, this.testregesteredUser.getPassword());
				psInsert.executeUpdate();
			}

			// cleaning and dropping the table
			try (PreparedStatement psInsert = connection.prepareStatement(sqlInsertProductIntoShoppingCard)) {
				psInsert.setString(1, this.testregesteredUser.getUsername());
				psInsert.setInt(2, notAddedProduct.getProductID());
				psInsert.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// test all products method
	@Test
	public void testOverView() {
		// getting a list of all products
		List<productDatabase> productList = DBFacade.getInstance().getProducts();

		assertEquals(1, productList.size());
		assertEquals(notAddedProduct.getPrice(), productList.get(0).getPrice());
		assertEquals(notAddedProduct.getProductID(), productList.get(0).getProductID());
		assertEquals(notAddedProduct.getProductQuantatity(), productList.get(0).getProductQuantatity());
		assertEquals(notAddedProduct.getProductName(), productList.get(0).getProductName());
		assertEquals(notAddedProduct.getProductDescription(), productList.get(0).getProductDescription());

	}

	// test regesterUserMethod
	@Test
	public void testRegsiterUser() {

		DBFacade.getInstance().registeringUser(testUserData);

		List<UserDatabase> listUsers = new ArrayList<>();

		// getting the inserted user using a separate connection
		try (Connection connection = DriverManager.getConnection(this.url)) {
			try (PreparedStatement psSelectUsers = connection
					.prepareStatement("select * from user where username = ?;")) {
				psSelectUsers.setString(1, testUserData.getUsername());
				ResultSet rs = psSelectUsers.executeQuery();
				while (rs.next()) {
					listUsers.add(new UserDatabase(rs.getString(1), rs.getString(2)));
				}
			} catch (SQLException sqlE) {
				sqlE.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// asserting they are equal
		assertEquals(1, listUsers.size());
		assertEquals(testUserData.getPassword(), listUsers.get(0).getPassword());
		assertEquals(testUserData.getUsername(), listUsers.get(0).getUsername());

	}

	// test regesterUserMethod
	@Test
	public void testAlreadyRegisteredUser() {

		// inserting the first user
		DBFacade.getInstance().registeringUser(testUserData);

		// declaring a new user with the same user name.
		UserDatabase testUserDatabaseAlreadyRegistered = new UserDatabase("mido", "4567");

		// trying to insert the new user that has the same username
		DBFacade.getInstance().registeringUser(testUserDatabaseAlreadyRegistered);

		List<UserDatabase> listUsers = new ArrayList<>();

		// getting the inserted user using a separate connection
		try (Connection connection = DriverManager.getConnection(this.url)) {
			try (PreparedStatement psSelectUsers = connection
					.prepareStatement("select * from user where username = ?;")) {
				psSelectUsers.setString(1, testUserDatabaseAlreadyRegistered.getUsername());
				ResultSet rs = psSelectUsers.executeQuery();
				while (rs.next()) {
					listUsers.add(new UserDatabase(rs.getString(1), rs.getString(2)));
				}
			} catch (SQLException sqlE) {
				sqlE.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// asserting they are equal
		assertEquals(1, listUsers.size());
		assertEquals(testUserData.getPassword(), listUsers.get(0).getPassword());
		assertEquals(testUserData.getUsername(), listUsers.get(0).getUsername());

	}

	@Test
	public void testDeleteProductsWithQuantatityZero() {

		// inserting the first user
		DBFacade.getInstance().noStock();

		List<productDatabase> products = new ArrayList<>();

		try (Connection connection = DriverManager.getConnection(this.url)) {

			try (PreparedStatement psSelectProducts = connection
					.prepareStatement("select * from product where productQuantity = 0;")) {
				ResultSet rs = psSelectProducts.executeQuery();
				while (rs.next()) {
					products.add(new productDatabase(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4),
							rs.getInt(5)));
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// asserting they are equal
		assertEquals(0, products.size());

		try (Connection connection = DriverManager.getConnection(this.url)) {

			try (PreparedStatement psSelectProducts = connection
					.prepareStatement("select * from shopping where username = ?;")) {
				psSelectProducts.setString(1, this.testregesteredUser.getUsername());
				ResultSet rs = psSelectProducts.executeQuery();
				assertEquals(0, rs.getFetchSize());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testAddProductToshoppingCart() {

		productDatabase p = new productDatabase(10, "testCartProduct", "product to test shoppingCart", 20, 3);

		try (Connection connection = DriverManager.getConnection(this.url)) {

			try (PreparedStatement psInsertProduct = connection.prepareStatement(
					"INSERT INTO product (productID, name, price, description, productQuantity) values (?,?, ?, ?, ?)")) {

				psInsertProduct.setInt(1, p.getProductID());
				psInsertProduct.setString(2, p.getProductName());
				psInsertProduct.setFloat(3, p.getPrice());
				psInsertProduct.setString(4, p.getProductDescription());
				psInsertProduct.setInt(5, p.getProductQuantatity());

				psInsertProduct.executeUpdate();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// inserting the first user
		try {
			DBFacade.getInstance().addingProduct(this.testregesteredUser.getUsername(), p.getProductID());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try (Connection connection = DriverManager.getConnection(this.url)) {

			try (PreparedStatement psSelectFromShopping = connection
					.prepareStatement("select * from shopping where username = ? AND productID = ?")) {
				psSelectFromShopping.setString(1, this.testregesteredUser.getUsername());
				psSelectFromShopping.setInt(2, p.getProductID());
				ResultSet rs = psSelectFromShopping.executeQuery();

				int rowCount = 0;
				while (rs.next()) {
					rowCount++;
				}

				assertEquals(1, rowCount);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testAddProductsMorethanFiveTimes() {

		List<productDatabase> products = new ArrayList<>();
		products.add(new productDatabase(10, "testCartProduct1", "product to test shoppingCart1", 20, 3));
		products.add(new productDatabase(11, "testCartProduct2", "product to test shoppingCart2", 20, 3));
		products.add(new productDatabase(12, "testCartProduct3", "product to test shoppingCart3", 20, 3));
		products.add(new productDatabase(13, "testCartProduct4", "product to test shoppingCart4", 20, 3));
		products.add(new productDatabase(14, "testCartProduct5", "product to test shoppingCart5", 20, 3));

		try (Connection connection = DriverManager.getConnection(this.url)) {
			for (productDatabase p : products) {
				try (PreparedStatement psInsertProduct = connection.prepareStatement(
						"INSERT INTO product (productID, name, price, description, productQuantity) values (?,?, ?, ?, ?)")) {

					psInsertProduct.setInt(1, p.getProductID());
					psInsertProduct.setString(2, p.getProductName());
					psInsertProduct.setFloat(3, p.getPrice());
					psInsertProduct.setString(4, p.getProductDescription());
					psInsertProduct.setInt(5, p.getProductQuantatity());

					psInsertProduct.executeUpdate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (productDatabase p : products) {
			try {
				DBFacade.getInstance().addingProduct(this.testregesteredUser.getUsername(), p.getProductID());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		
		try (Connection connection = DriverManager.getConnection(this.url)) {

			try (PreparedStatement psSelectFromShopping = connection
					.prepareStatement("select * from shopping where username = ? ;")) {
				psSelectFromShopping.setString(1, this.testregesteredUser.getUsername());
				ResultSet rs = psSelectFromShopping.executeQuery();
				int rowCount = 0;
				while (rs.next()) {
					rowCount++;
				}

				assertEquals(5, rowCount);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Test
	public void testAddSameProductTwice() {
		
		try {
			DBFacade.getInstance().addingProduct(this.testregesteredUser.getUsername(), this.notAddedProduct.getProductID());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		
		try (Connection connection = DriverManager.getConnection(this.url)) {

			try (PreparedStatement psSelectFromShopping = connection
					.prepareStatement("select * from shopping where username = ? AND productID = ?")) {
				psSelectFromShopping.setString(1, this.testregesteredUser.getUsername());
				psSelectFromShopping.setInt(2, this.notAddedProduct.getProductID());
				ResultSet rs = psSelectFromShopping.executeQuery();

				int rowCount = 0;
				while (rs.next()) {
					rowCount++;
				}

				assertEquals(1, rowCount);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
