package CRM.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import CRM.config.MysqlConfig;
import ProjectCRM.Model.User;

import java.util.*;
public class UserRepository {
	public static List<User> checkLogin(String email, String password) 
			throws SQLException, ClassNotFoundException {
		User user = null;
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		List<User> users = new ArrayList<>();
		try {
			connection = MysqlConfig.connect();
			
			String selectUser = "SELECT * FROM Users WHERE email =  ? and password = ?";
			preparedStatement = connection.prepareStatement(selectUser);
			// Truyền tham số vào câu query nếu có
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			//Thực thi câu query
			// ExecuteQuery: Khi câu truy vấn là select
			//ExecuteUpdate: Không phải là câu lấy dữ liệu INSERT,DELETE,UPDATE
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				user = new User();
				user.setEmail(resultSet.getString("email"));
				user.setPassword(resultSet.getString("password"));
				users.add(user);
			} 
		} catch(SQLException e) {
			System.out.println("Ket noi that bai " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
	            if (resultSet != null) resultSet.close();
	            if (preparedStatement != null) preparedStatement.close();
	            if (connection != null) connection.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}		
		return users;
	}
}
