package CRM.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import CRM.config.MysqlConfig;
import ProjectCRM.Model.StatusTask;
public class StatusReposiroty {
	public static List<StatusTask> getAllStatus(){
		List<StatusTask> status = new ArrayList<>();

		StatusTask statusTask = null;
		ResultSet resultSet = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = MysqlConfig.connect();

			String selectAllStatus = "SELECT * FROM Status;";

			preparedStatement = connection.prepareStatement(selectAllStatus);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				statusTask = new StatusTask();
				statusTask.setStatusID(resultSet.getInt("StatusID"));
				statusTask.setStatusName(resultSet.getString("StatusName"));
				
				status.add(statusTask);
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
		return status;
	}
}
