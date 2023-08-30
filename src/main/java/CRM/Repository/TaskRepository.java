package CRM.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import CRM.config.MysqlConfig;
import ProjectCRM.Model.Project;
import ProjectCRM.Model.Role;
import ProjectCRM.Model.StatusTask;
import ProjectCRM.Model.Task;
import ProjectCRM.Model.User;

public class TaskRepository {
	public static boolean addTask(Task task) {
		boolean affectedRow = false;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet generateKeys = null;

		try {
			String addTask = "INSERT INTO Tasks(TaskName, StartDate, EndDate, ExecutorID, ProjectID, StatusID)\n"
					+ " VALUES(?, ?, ?, ?, ?, ?);";

			connection = MysqlConfig.connect();
			preparedStatement = connection.prepareStatement(addTask, Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, task.getTaskName());
			preparedStatement.setTimestamp(2, new java.sql.Timestamp(task.getStartDate().getTime()));
			preparedStatement.setTimestamp(3, new java.sql.Timestamp(task.getEndDate().getTime()));
			System.out.println("StartDate: " + task.getStartDate());
			System.out.println("EndDate: " + task.getEndDate());

			preparedStatement.setInt(4, task.getExecutorId());
			preparedStatement.setInt(5, task.getProjectId());
			preparedStatement.setInt(6, task.getStatusId());

			affectedRow = preparedStatement.executeUpdate() > 0;
			System.out.println("Affected rows: " + affectedRow);
			// Lấy id của task mới được tạo
			generateKeys = preparedStatement.getGeneratedKeys();
			if (generateKeys.next()) {
				int taskId = generateKeys.getInt(1);

				// Thêm vào bảng TaskStatusHistory
				String addTaskHistory = "INSERT INTO TaskStatusHistory(TaskID, StatusID) VALUES(?, ?)";
				try (PreparedStatement preparedStatementHistory = connection.prepareStatement(addTaskHistory)) {
					preparedStatementHistory.setInt(1, taskId);
					preparedStatementHistory.setInt(2, task.getStatusId());
					preparedStatementHistory.executeUpdate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception: " + e.getMessage());
		} finally {
			try {
				if (generateKeys != null)
					generateKeys.close();
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Exception: " + e.getMessage());
			}
		}

		return affectedRow;
	}

	public static List<Task> getTasks() {
		List<Task> tasks = new ArrayList<>();

		Task task = null;
		Project project = null;
		User user = null;
		StatusTask statusTask = null;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			String selectTask = "SELECT \n" + "    T.TaskID,\n" + "    T.TaskName,\n" + "    P.ProjectName,\n"
					+ "    U.Fullname AS ExecutorName,\n" + "    T.StartDate,\n" + "    T.EndDate,\n"
					+ "    S.StatusName\n" + "FROM Tasks T\n" + "JOIN Projects P ON T.ProjectID = P.ProjectID\n"
					+ "JOIN Users U ON T.ExecutorID = U.UserID\n" + "JOIN TaskStatusHistory H ON T.TaskID = H.TaskID\n"
					+ "JOIN Status S ON H.StatusID = S.StatusID\n" + "ORDER BY T.TaskID;";

			connection = MysqlConfig.connect();
			preparedStatement = connection.prepareStatement(selectTask);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				task = new Task();
				project = new Project();
				user = new User();
				statusTask = new StatusTask();

				task.setTaskId(resultSet.getInt("TaskID"));
				task.setTaskName(resultSet.getString("TaskName"));

				project.setProjectName(resultSet.getString("ProjectName"));
				user.setFullName(resultSet.getString("ExecutorName"));
				task.setStartDate(resultSet.getTimestamp("StartDate"));
				task.setEndDate(resultSet.getTimestamp("EndDate"));
				statusTask.setStatusName(resultSet.getString("StatusName"));

				// Thực hiện thêm các thuộc tính vào lớp task
				task.setProject(project);
				task.setUser(user);
				task.setStatusTask(statusTask);

				tasks.add(task);
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
		return tasks;
	}

	public static boolean updateTask(Task task) {
		boolean affectedRow = false;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			String updateTask = "UPDATE Tasks SET TaskName = ?, StartDate = ?, EndDate = ?, "
					+ "ExecutorID = ?, ProjectID = ?, StatusID = ? WHERE TaskID = ?;";
			connection = MysqlConfig.connect();
			preparedStatement = connection.prepareStatement(updateTask);

			preparedStatement.setString(1, task.getTaskName());
			preparedStatement.setTimestamp(2, new java.sql.Timestamp(task.getStartDate().getTime()));
			preparedStatement.setTimestamp(3, new java.sql.Timestamp(task.getEndDate().getTime()));
			preparedStatement.setInt(4, task.getExecutorId());
			preparedStatement.setInt(5, task.getProjectId());
			preparedStatement.setInt(6, task.getStatusId());
			preparedStatement.setInt(7, task.getTaskId());

			affectedRow = preparedStatement.executeUpdate() > 0;

			if (affectedRow) {
				String addTaskHistory = "INSERT INTO TaskStatusHistory(TaskID, StatusID)\n" + "VALUES(?, ?); ";
				try (PreparedStatement preparedStatementHistory = connection.prepareStatement(addTaskHistory)) {
					preparedStatementHistory.setInt(1, task.getTaskId());
					preparedStatementHistory.setInt(2, task.getStatusId());
					preparedStatementHistory.executeUpdate();
				}
			}
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
				System.out.println("Exception: " + e.getMessage());
			}
		}
		return affectedRow;
	}

	public static Task getTaskById(int taskId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Task task = null;
		User user = null;
		Project project = null;
		StatusTask statusTask = null;

		try {
			String selectTask = "SELECT Tasks.*, Users.Fullname , Projects.ProjectName , Status.StatusName \n"
					+ "FROM Tasks\n" + "JOIN Users ON Tasks.ExecutorID = Users.UserID \n"
					+ "JOIN Projects ON Tasks.ProjectID = Projects.ProjectID \n"
					+ "JOIN Status ON Tasks.StatusID = Status.StatusID \n" + "WHERE Tasks.TaskID = ?;";
			connection = MysqlConfig.connect();
			preparedStatement = connection.prepareStatement(selectTask);
			preparedStatement.setInt(1, taskId);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				task = new Task();
				user = new User();
				project = new Project();
				statusTask = new StatusTask();

				task.setTaskId(resultSet.getInt("TaskID"));
				task.setTaskName(resultSet.getString("TaskName"));
				task.setStartDate(resultSet.getTimestamp("StartDate"));
				task.setEndDate(resultSet.getTimestamp("EndDate"));
				task.setExecutorId(resultSet.getInt("ExecutorID"));
				task.setProjectId(resultSet.getInt("ProjectID"));
				task.setStatusId(resultSet.getInt("StatusID"));

				// Lưu thêm thông tin vào đối tượng task
				user.setFullName(resultSet.getString("Fullname"));
				project.setProjectName(resultSet.getString("ProjectName"));
				statusTask.setStatusName(resultSet.getString("StatusName"));

				task.setUser(user);
				task.setProject(project);
				task.setStatusTask(statusTask);
			}
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
				System.out.println("Exception: " + e.getMessage());
			}
		}
		return task;
	}

	public static List<User> getAllUsers() {
		List<User> users = new ArrayList<>();

		User user = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = MysqlConfig.connect();

			String selectAllUsers = "SELECT Users.UserID,Users.Fullname \n" + "FROM Users\n";

			preparedStatement = connection.prepareStatement(selectAllUsers);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				user = new User();
				user.setId(resultSet.getInt("UserID"));
				user.setFullName(resultSet.getString("Fullname"));

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

	public static List<Project> getAllProjects() {
		List<Project> projects = new ArrayList<>();

		Project project = null;
		ResultSet resultSet = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = MysqlConfig.connect();

			String selectAllProjects = "SELECT Projects.ProjectID,Projects.ProjectName \n" + "FROM Projects;";
			preparedStatement = connection.prepareStatement(selectAllProjects);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				project = new Project();
				project.setProjectId(resultSet.getInt("ProjectID"));
				project.setProjectName(resultSet.getString("ProjectName"));

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

	public static List<StatusTask> getAllStatusTask() {
		List<StatusTask> statusTasks = new ArrayList<>();

		StatusTask statusTask = null;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			String selectStatusTask = "SELECT Status.StatusID ,Status.StatusName FROM Status;";

			connection = MysqlConfig.connect();
			preparedStatement = connection.prepareStatement(selectStatusTask);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				statusTask = new StatusTask();

				statusTask.setStatusID(resultSet.getInt("StatusID"));
				statusTask.setStatusName(resultSet.getString("StatusName"));

				statusTasks.add(statusTask);
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
		return statusTasks;
	}

	public static boolean deleteTask(int taskId) {
		boolean affectedRow = false;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Task task = null;
		try {
			connection = MysqlConfig.connect();

			// Xoa bang tu taskhistory
			String deleteTaskHistory = "DELETE FROM TaskStatusHistory WHERE TaskID = ?;";
			preparedStatement = connection.prepareStatement(deleteTaskHistory);
			preparedStatement.setInt(1, taskId);
			preparedStatement.executeUpdate();

			// xoa bao tu bang task
			String deleteTask = "DELETE FROM Tasks WHERE TaskID = ?;";
			preparedStatement = connection.prepareStatement(deleteTask);
			preparedStatement.setInt(1, taskId);
			affectedRow = preparedStatement.executeUpdate() > 0;
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
		return affectedRow;
	}

}
