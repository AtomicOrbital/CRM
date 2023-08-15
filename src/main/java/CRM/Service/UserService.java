package CRM.Service;

import java.sql.SQLException;

import ProjectCRM.Model.User;
import java.util.*;

import CRM.Repository.UserRepository;
public class UserService {
	
	public User authenticateUser(String email, String password) throws SQLException, ClassNotFoundException {
		List<User> users = UserRepository.checkLogin(email, password);
		if(users != null && !users.isEmpty()) {
			return users.get(0);
		}
		return null;
	}
}
