package ProjectCRM.Controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import CRM.Repository.UserRepository;
import CRM.Service.UserService;
import ProjectCRM.Model.User;

import javax.servlet.http.Cookie;

import java.util.*;
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
	private UserService userService = new UserService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		try {
			User users = userService.authenticateUser(email, password);
			if(users != null) {
				
				String encodeEmail = URLEncoder.encode(users.getEmail(), "UTF-8");
				String encodePassword = URLEncoder.encode(users.getPassword(), "UTF-8");
				
				Cookie emailCookie = new Cookie("userEmail", encodeEmail);
				Cookie passwordCookie = new Cookie("userPassword", encodePassword);
				
				emailCookie.setMaxAge(60*60);
				passwordCookie.setMaxAge(60*60);
				// thêm cookie vào phản hồi
				response.addCookie(emailCookie);
				response.addCookie(passwordCookie);
				System.out.println("User " + users.getEmail() + " logged is successfully");
				response.sendRedirect("index.jsp");
			} else {
				System.out.println("Failed login for email: " + email);
				response.sendRedirect("login.jsp?error=true");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
