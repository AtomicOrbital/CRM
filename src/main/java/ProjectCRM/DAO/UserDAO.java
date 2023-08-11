package ProjectCRM.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ProjectCRM.Model.User;

public class UserDAO {
	public static User checkLogin(String email, String password) 
			throws SQLException, ClassNotFoundException {
		String jdbcURL = "jdbc:mysql://127.0.0.1:3306/CRM";
		String jdbcUsername = "root";
		String jdbcPassword = "1032001";

		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		
		String sql = "SELECT * FROM Users WHERE email =  ? and password = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, email);
		preparedStatement.setString(2, password);
		
		ResultSet result = preparedStatement.executeQuery();
		
		User user = null;
		
		if(result.next()) {
			user = new User();
			user.setEmail(result.getString("email"));
			user.setPassword(result.getString("password"));
		}
		
		connection.close();
		
		return user;
	}
}
