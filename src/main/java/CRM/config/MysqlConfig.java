package CRM.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlConfig {
	private static final String jdbcURL = "jdbc:mysql://127.0.0.1:3306/CRM";
	private static final String jdbcUsername = "root";
	private static final String jdbcPassword = "1032001";
	
	public static Connection connect() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL,jdbcUsername, jdbcPassword);
		} catch (Exception e) {
			System.out.println("connect failed ");
			e.printStackTrace();
		}
		return connection;
	}
}
