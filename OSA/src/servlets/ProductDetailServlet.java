package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.OnlineShopApp;
import dbadapter.productDatabase;


// details page of the product
public class ProductDetailServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * doGet contains the call for the index webpage
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		
		//checks if there is a logged in user.
		if (OnlineShopApp.getInstance().getLoggedUserName().equals("No Loggedin user")) {
			FeedbackServlet error = new FeedbackServlet("there is no logged in user go to Registeration first", "Error",
					false);
			error.doGet(request, response);
		}
		
		// gets the productId from the query string 
		int productID = Integer.parseInt(request.getParameter("pid"));
		
		// get the product Entity from the DB
		productDatabase product = OnlineShopApp.getInstance().getProductByID(productID);

		if (product == null) {
			// No product -> can be deleted -> raise an error
			FeedbackServlet error = new FeedbackServlet("Product does not exist with the id : " + productID, "Error",
					false);
			error.doGet(request, response);
		}

		request.setAttribute("product", product);
		request.setAttribute("pagetitle", "Productdetails : " + product.getProductID());
		request.setAttribute("LoggedUser", OnlineShopApp.getInstance().getLoggedUserName());
		try {
			request.getRequestDispatcher("/templates/ProductDetails.ftl").forward(request, response);

		} catch (ServletException | IOException e) {
			request.setAttribute("errormessage", "Template error: please contact the administrator");
			e.printStackTrace();

		}

	}
	
	// Do post method to save the peoduct to the shopping cart of the logged in user. 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {

		int productID = Integer.parseInt(request.getParameter("pid"));

		OnlineShopApp osa = OnlineShopApp.getInstance();
		
		try {
			// if we could save the product -> success
			if (osa.forwardAddProductToCart(osa.getLoggedUserName(), productID)) {
				
				request.setAttribute("value", true);
				request.setAttribute("pagetitle", "Success");
				request.setAttribute("message", "Product Added successfully to the shopping cart");
				request.setAttribute("LoggedUser", OnlineShopApp.getInstance().getLoggedUserName());
	            request.getRequestDispatcher("/templates/ResultFeedback.ftl").forward(request, response);
	            return; // Return after forwarding


			}
		} 
		catch (Exception ex) {

			request.setAttribute("value", false);
			request.setAttribute("pagetitle", "Error");
			request.setAttribute("message", ex.getMessage());
			request.setAttribute("LoggedUser", OnlineShopApp.getInstance().getLoggedUserName());
            try {
				request.getRequestDispatcher("/templates/ResultFeedback.ftl").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return; // Return after forwarding
		}

		doGet(request, response);
	}
}
