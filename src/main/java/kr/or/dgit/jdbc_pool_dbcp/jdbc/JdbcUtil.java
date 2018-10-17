package kr.or.dgit.jdbc_pool_dbcp.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcUtil {

	public static void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void rollback(Connection connection) {
		if (connection != null) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
