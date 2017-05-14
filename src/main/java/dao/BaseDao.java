package dao;

import java.sql.Connection;

abstract class BaseDao {
	private Connection connection;

	BaseDao() {
		this.setConnection(SqliteJDBCConnector.connection());
	}

	private void setConnection(Connection connection) {
		this.connection = connection;
	}
}
