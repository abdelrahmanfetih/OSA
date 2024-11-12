package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import application.OnlineShopApp;

public class Registration extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		request.setAttribute("pagetitle", "Registration");
		request.setAttribute("LoggedUser", OnlineShopApp.getInstance().getLoggedUserName());
		try {
			request.getRequestDispatcher("/templates/Registration.ftl").forward(request, response);

		} catch (ServletException | IOException e) {
			request.setAttribute("errormessage", "Template error: please contact the administrator");
			e.printStackTrace();
		}

	}

	/**
	 * Call doGet instead of doPost
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {

		OnlineShopApp osApp = OnlineShopApp.getInstance();


		if (osApp.registerUser(request.getParameter("username") , request.getParameter("password"))) {
			osApp.setLoggedUserName(request.getParameter("username"));
			FeedbackServlet feedback = new FeedbackServlet("The user is registerd successfully", "Success", true);
			feedback.doGet(request, response);
		} else {
			String msg = "Register error: Username must be unique.";
			FeedbackServlet error = new FeedbackServlet(msg, "Error Adding", false);
			error.doGet(request, response);
		}

	}
}
