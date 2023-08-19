package CRM.Service;

import java.sql.SQLException;

import ProjectCRM.Model.User;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import CRM.Repository.UserRepository;
public class UserService {
	
	public User authenticateUser(HttpServletRequest request,String email, String password) 
			throws SQLException, ClassNotFoundException {
		List<User> users = UserRepository.checkLogin(email, password);
		
		if(users != null && !users.isEmpty()) {
			User user = users.get(0);
			HttpSession session = request.getSession();
			session.setAttribute("roleName", user.getRole().getRoleName());
			return user;
		}
		return null;
	}
}
