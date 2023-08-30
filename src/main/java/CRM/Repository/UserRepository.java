package CRM.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import CRM.config.MysqlConfig;
import ProjectCRM.Model.User;

import ProjectCRM.Model.Role;
import java.util.*;

public class UserRepository {
	public static List<User> checkLogin(String email, String password) throws SQLException, ClassNotFoundException {
		User user = null;
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		List<User> users = new ArrayList<>();
		try {
			connection = MysqlConfig.connect();

			String selectUser = "SELECT *" + "FROM Users " + "JOIN Roles r ON r.RoleID = Users.RoleID\n"
					+ "WHERE email = ? AND password = ?";
			preparedStatement = connection.prepareStatement(selectUser);
			// Truyền tham số vào câu query nếu có
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			// Thực thi câu query
			// ExecuteQuery: Khi câu truy vấn là select
			// ExecuteUpdate: Không phải là câu lấy dữ liệu INSERT,DELETE,UPDATE
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				user = new User();
				user.setEmail(resultSet.getString("email"));
				user.setPassword(resultSet.getString("password"));

				Role role = new Role();
				role.setRoleName(resultSet.getString("roleName"));
				user.setRole(role);

				users.add(user);
			}
		} catch (SQLException e) {
			System.out.println("Ket noi that bai " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return users;
	}

	public static List<User> getAllUsersWithRoles() {
		List<User> users = new ArrayList<>();

		User user = null;
		Role role = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = MysqlConfig.connect();

			String selectAllUsersRoles = "SELECT Users.UserID, Users.email, Users.password ,Users.Fullname, Users.Address, Users.Phone, Roles.RoleName\n"
					+ "FROM Users\n"
					+ "JOIN Roles ON Users.RoleID = Roles.RoleID";

			preparedStatement = connection.prepareStatement(selectAllUsersRoles);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				user = new User();
				user.setId(resultSet.getInt("UserID"));
				user.setEmail(resultSet.getString("email"));
				user.setPassword(resultSet.getString("password"));
				user.setFullName(resultSet.getString("Fullname"));
				user.setAddress(resultSet.getString("Address"));
				user.setPhone(resultSet.getString("Phone"));

				role = new Role();
				role.setRoleName(resultSet.getString("RoleName"));
				user.setRole(role);

				users.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return users;
	}

	public static boolean insertUser(User user) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = MysqlConfig.connect();
			String insertUser = "INSERT INTO Users(email, password, Fullname, Address, Phone, RoleID) "
					+ "VALUES (?, ?, ?, ?, ?, ?)";
			preparedStatement = connection.prepareStatement(insertUser);
			
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getFullName());
			preparedStatement.setString(4, user.getAddress());		
			preparedStatement.setString(5, user.getPhone());
			preparedStatement.setInt(6, user.getId());
			
			return  preparedStatement.executeUpdate() > 0;
			 
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
	        try {
	            if (preparedStatement != null) preparedStatement.close();
	            if (connection != null) connection.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	public static boolean deleteUser(int userId) {
		boolean rowDeleteId = false;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			String deleteUser = "DELETE FROM Users WHERE UserID = ?";
			
			connection = MysqlConfig.connect();
			preparedStatement = connection.prepareStatement(deleteUser);
			preparedStatement.setInt(1, userId);
			
			rowDeleteId = preparedStatement.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowDeleteId;
	}
	
	public static boolean updateUser(User user) {
	    boolean affectedRow = false;
	    
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    
	    try {
	        String updateUser = "UPDATE Users "
	                + "SET email=?, password=?, fullName=?, address=?, phone=?, roleId=? " 
	                + " WHERE UserID = ?;";
	        
	        connection = MysqlConfig.connect();
	        preparedStatement = connection.prepareStatement(updateUser);
	        
	        preparedStatement.setString(1, user.getEmail());
	        preparedStatement.setString(2, user.getPassword());
	        preparedStatement.setString(3, user.getFullName());
	        preparedStatement.setString(4, user.getAddress());
	        preparedStatement.setString(5, user.getPhone());
	        preparedStatement.setInt(6, user.getRole().getRoleID());
	        preparedStatement.setInt(7, user.getId());
	        
	        affectedRow = preparedStatement.executeUpdate() > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return affectedRow;
	}
	
	
}
