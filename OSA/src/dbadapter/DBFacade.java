package dbadapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import interfaces.IProductDatabase;
import interfaces.IShoppingCartDatabase;
import interfaces.IUserDatabase;

/**
 * Class represnts the DB facade design patterm .
 * 
 */
public class DBFacade implements IProductDatabase, IUserDatabase, IShoppingCartDatabase {

	private static DBFacade instance;

	// connection string.
	private String url = "jdbc:mysql://127.0.0.1:3306/Osa?user=root&password=" + Configuration.getPassword()
			+ "&useUnicode=true&characterEncoding=UTF-8"
			+ "&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=GMT&useSSL=false";

	/**
	 * Constructor which loads the corresponding driver for the chosen database type
	 */
	private DBFacade() {
		try {
			Class.forName("com." + Configuration.getType() + ".jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Implementation of the Singleton pattern.
	 * 
	 * @return instance of the DBFacade class
	 */
	public static DBFacade getInstance() {

		if (instance == null)
			instance = new DBFacade();

		return instance;
	}

	public static void setInstance(DBFacade dbfacade) {
		instance = dbfacade;
	}

	/**
	 * Function that stores the regesitered user data in the database .
	 * 
	 */
	@Override
	public Boolean registeringUser(UserDatabase ud) {

		// query to check if the usre already exists
		String queryUserData = "SELECT * FROM user WHERE username = ?";

		// query to insert the user if it does not exist before with the same username
		String insertUserData = "INSERT INTO user (username, password) values (?, ?)";

		// open connection
		try (Connection connection = DriverManager.getConnection(this.url)) {

			// prepared statment
			try (PreparedStatement ps = connection.prepareStatement(queryUserData)) {

				ps.setString(1, ud.getUsername());

				try (ResultSet rs = ps.executeQuery()) {
					// if the user does not exits before -> register
					if (rs.next() == false) {
						try (PreparedStatement ps2 = connection.prepareStatement(insertUserData)) {
							ps2.setString(1, ud.getUsername());
							ps2.setString(2, ud.getPassword());
							ps2.executeUpdate();
						} catch (Exception e) {
							e.printStackTrace();
							return false;
						}

					}
					// the user does exist before -> return false
					else {
						return false;
					}

				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Function returns a list of all products in a descending order by price .
	 * 
	 */
	@Override
	public List<productDatabase> getProducts() {

		List<productDatabase> result = new ArrayList<productDatabase>();

		// Declare the necessary SQL queries.
		String sqlSelect = "Select * from product order by price desc";

		// open connection with the DB
		try (Connection connection = DriverManager.getConnection(this.url)) {

			// declare preParerd Statment
			try (PreparedStatement ps = connection.prepareStatement(sqlSelect)) {

				// storing the returned products in a list
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						productDatabase temp = new productDatabase(rs.getInt(1), rs.getString(2), rs.getString(3),
								rs.getFloat(4), rs.getInt(5));
						result.add(temp);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/*
	 * Adds a product to the shopping cart of a specific user
	 * @return true if the adding was successful and fasle otherwise. 
	 * */
	@Override
	public Boolean addingProduct(String username, int productID) throws SQLException {

		// query to insert the user if it does not exist before with the same username
		String insertItemToShoppingCart = "INSERT INTO shopping (username, productID) values (?, ?)";

		// open connection
		try (Connection connection = DriverManager.getConnection(this.url)) {
			// prepared statment
			try (PreparedStatement ps = connection.prepareStatement(insertItemToShoppingCart)) {
				ps.setString(1, username);
				ps.setInt(2, productID);
				ps.executeUpdate();
				return true;
			}

		} catch (SQLException e) {
			//e.printStackTrace();
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
		productDatabase product = null;

		// gets the product by id
		String queryProductData = "SELECT * FROM product WHERE productID = ?";

		// open connection
		try (Connection connection = DriverManager.getConnection(this.url)) {

			// prepared statment
			try (PreparedStatement ps = connection.prepareStatement(queryProductData)) {
				ps.setInt(1, id);
				// storing the returned products in a list
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						product = new productDatabase(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4),
								rs.getInt(5));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return product;
	}

	
	/**
	 * check all the products that have  0 quantatity and removes them from the shopping cart and from the proudct table
	 * 	 * 
	 *  */
	@Override
	public void noStock() {
		
		String sqlDeleteShoppingCart = "DELETE FROM shopping WHERE productID IN (SELECT productID FROM product WHERE ProductQuantity = 0); ";
		
		String sqlDeleteProduct = "DELETE FROM product WHERE ProductQuantity = 0 ; ";

		// open connection with the DB
		try (Connection connection = DriverManager.getConnection(this.url)) {

			// declare preParerd Statment
			try (PreparedStatement ps = connection.prepareStatement(sqlDeleteShoppingCart)) {
				// commit deleting from the shopping cart
				ps.executeUpdate();
			}

			// declare preParerd Statment
			try (PreparedStatement ps = connection.prepareStatement(sqlDeleteProduct)) {
				// commit deleting from the prodcut table
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}