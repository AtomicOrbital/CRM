package CRM.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import CRM.config.MysqlConfig;
import ProjectCRM.Model.Role;
import java.util.*;
public class RoleRepository {
	
	
	public List<Role>getAllRoles(){
		List<Role> roles = new ArrayList<>();
		String selectRoles = "SELECT RoleID, RoleName, Description FROM Roles";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = MysqlConfig.connect();
			preparedStatement = connection.prepareStatement(selectRoles);
			// Thực thi câu query
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Role role = new Role();
				role.setRoleID(resultSet.getInt("RoleID"));
				role.setRoleName(resultSet.getString("RoleName"));
				role.setDescription(resultSet.getString("Description"));
				roles.add(role);
			}
		} catch (Exception e) {
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
		return roles;
	}
	
	public void addRole(Role role) {
		String insertRole = "INSERT INTO Roles(RoleName, Description) VALUES (?, ?)";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = MysqlConfig.connect();
			preparedStatement = connection.prepareStatement(insertRole);
			
			preparedStatement.setString(1, role.getRoleName());
			preparedStatement.setString(2, role.getDescription());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
	        try {
	            if (preparedStatement != null) preparedStatement.close();
	            if (connection != null) connection.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
}
