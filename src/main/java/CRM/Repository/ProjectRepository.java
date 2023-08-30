package CRM.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import CRM.config.MysqlConfig;
import ProjectCRM.Model.Project;

import java.util.*;

public class ProjectRepository {
	public static boolean addProject(Project project) {
		boolean affectedRow = false;

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			String addProjectSql = "INSERT INTO Projects(ProjectName, StartDate, EndDate) VALUES(?, ?, ?);";

			connection = MysqlConfig.connect();
			preparedStatement = connection.prepareStatement(addProjectSql);

			preparedStatement.setString(1, project.getProjectName());
			preparedStatement.setDate(2, new java.sql.Date(project.getStartDate().getTime()));
			preparedStatement.setDate(3, new java.sql.Date(project.getEndDate().getTime()));

			affectedRow = preparedStatement.executeUpdate() > 0;
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
		return affectedRow;
	}

	public static List<Project> getAllProjects() {
		List<Project> projects = new ArrayList<>();

		Project project = null;
		ResultSet resultSet = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = MysqlConfig.connect();

			String selectAllProjects = "SELECT Projects.ProjectID,Projects.ProjectName, Projects.StartDate ,Projects.EndDate \n"
					+ "FROM Projects;";
			preparedStatement = connection.prepareStatement(selectAllProjects);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				project = new Project();
				project.setProjectId(resultSet.getInt("ProjectID"));
				project.setProjectName(resultSet.getString("ProjectName"));
				project.setStartDate(resultSet.getDate("StartDate"));
				project.setEndDate(resultSet.getDate("EndDate"));

				projects.add(project);
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
		return projects;
	}

	public static boolean updateProject(Project project) {
		boolean affectedRow = false;

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			String updateProject = "UPDATE Projects SET ProjectName = ?, StartDate = ?, EndDate = ? WHERE ProjectID = ?;";

			connection = MysqlConfig.connect();
			preparedStatement = connection.prepareStatement(updateProject);

			preparedStatement.setString(1, project.getProjectName());
			preparedStatement.setDate(2, new java.sql.Date(project.getStartDate().getTime()));
			preparedStatement.setDate(3, new java.sql.Date(project.getEndDate().getTime()));
			preparedStatement.setInt(4, project.getProjectId());
			affectedRow = preparedStatement.executeUpdate() > 0;
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
		return affectedRow;
	}

	public static boolean deleteProject(int projectId) {
		boolean rowDeleteId = false;

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			String deleteProject = "DELETE FROM Projects WHERE ProjectID = ?;";

			connection = MysqlConfig.connect();
			preparedStatement = connection.prepareStatement(deleteProject);
			preparedStatement.setInt(1, projectId);

			rowDeleteId = preparedStatement.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowDeleteId;
	}
}
