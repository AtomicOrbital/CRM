package CRM.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import CRM.config.MysqlConfig;
import ProjectCRM.Model.Role;
import java.util.*;

public class RoleRepository {

	public List<Role> getAllRoles() {
		List<Role> roles = new ArrayList<>();
		String selectRoles = "SELECT * FROM Roles";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = MysqlConfig.connect();
			preparedStatement = connection.prepareStatement(selectRoles);
			// Thực thi câu query
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
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
		return roles;
	}

	public void addRole(Role role) {
		String insertRole = "INSERT INTO Roles(RoleName, Description) VALUES (?, ?)";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
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
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public int deleteId(int id) {
		int count = 0;

		String deleteByIdRole = "DELETE FROM Roles WHERE RoleID = ?";
		Connection connection = MysqlConfig.connect();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(deleteByIdRole);
			preparedStatement.setInt(1, id);

			count = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public boolean updateRole(Role role) {
		String updateRole = "UPDATE Roles SET RoleName = ?, Description = ? WHERE RoleID = ?;";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		boolean affectedRow = false;
		try {
			connection = MysqlConfig.connect();
			preparedStatement = connection.prepareStatement(updateRole);

			preparedStatement.setString(1, role.getRoleName());
			preparedStatement.setString(2, role.getDescription());
			preparedStatement.setInt(3, role.getRoleID());

			affectedRow = preparedStatement.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return affectedRow;
	}
}
