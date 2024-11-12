package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.OnlineShopApp;
import dbadapter.productDatabase;


public class RegisteredCustomerViewProductsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * doGet contains the insertOffer form
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		
	       if (OnlineShopApp.getInstance().getLoggedUserName().equals("No Loggedin user"))
	       {
	    	   FeedbackServlet error = new FeedbackServlet("there is no logged in user go to Registeration first","Error", false);
				error.doGet(request, response);
	       }
			//Only for testing 
	       else {
	       //System.out.println("hi i got here");
	       OnlineShopApp osApp =  OnlineShopApp.getInstance();
		       List<productDatabase> productsQuery = osApp.forwardBrowseProducts();
		       
		        request.setAttribute("products", productsQuery);
		        request.setAttribute("pagetitle", "Products Overview");
		        request.setAttribute("LoggedUser", OnlineShopApp.getInstance().getLoggedUserName());
		        try {
		        	 request.getRequestDispatcher("/templates/ProductOverView.ftl").forward(request, response);
		        	 
				} catch (ServletException | IOException e) {
					request.setAttribute("errormessage",
							"Template error: please contact the administrator");
					e.printStackTrace();
					
				}
	       }

	}
	/**
	 * Contains handling of Products call
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		
		doGet(request, response);doGet(request, response);
	}
}