//package ProjectCRM.DAO;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import CRM.config.MysqlConfig;
//import ProjectCRM.Model.User;
//
//public class UserDAO {
//	public static User checkLogin(String email, String password) 
//			throws SQLException, ClassNotFoundException {
//		User user = null;
//		Connection connection = null;
//		
//		try {
//			connection = MysqlConfig.connect();
//			
//			String sql = "SELECT * FROM Users WHERE email =  ? and password = ?";
//			PreparedStatement preparedStatement = connection.prepareStatement(sql);
//			// Truyền tham số vào câu query nếu có
//			preparedStatement.setString(1, email);
//			preparedStatement.setString(2, password);
//			//Thực thi câu query
//			// ExecuteQuery: Khi câu truy vấn là select
//			//ExecuteUpdate: Không phải là câu lấy dữ liệu INSERT,DELETE,UPDATE
//			ResultSet result = preparedStatement.executeQuery();
//				
//			if(result.next()) {
//				user = new User();
//				user.setEmail(result.getString("email"));
//				user.setPassword(result.getString("password"));
//			} 
//		}catch(SQLException e) {
//			System.out.println("Ket noi that bai " + e.getMessage());
//			e.printStackTrace();
//		} finally {
//			if(connection != null) {
//				try {
//					connection.close();
//				} catch (SQLException e) {
//					System.out.println("That bai dong ket noi " + e.getMessage());
//				}				
//			}
//		}		
//		return user;
//	}
//}
