package ProjectCRM.Controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ProjectCRM.DAO.UserDAO;
import ProjectCRM.Model.User;

import javax.servlet.http.Cookie;
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		try {
			User user = UserDAO.checkLogin(email, password);
			if(user != null) {
				Cookie loginCookie = new Cookie("user", user.getEmail());
				loginCookie.setMaxAge(60*60);
				response.addCookie(loginCookie);
				response.sendRedirect("profile.jsp");
			} else {
				response.sendRedirect("login.jsp");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
