package dao;

import java.sql.*;
import java.util.Map;

public class SqliteJDBCConnector {

	static Connection connection() {
		Connection connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/database.db");
		} catch (SQLException e) {
			System.out.println("Connection to DB failed");
		}

		return connection;
	}

	public static void createTables() throws SQLException {
		Connection connection = connection();
		Statement statement = connection.createStatement();
		statement.execute("CREATE TABLE IF NOT EXISTS categories\n" +
				"(\n" +
				"    id INTEGER PRIMARY KEY,\n" +
				"    name TEXT NOT NULL\n" +
				");\n" +
				"CREATE UNIQUE INDEX categories_name_uindex ON categories (name);\n" +
				");");
		statement.execute("CREATE TABLE IF NOT EXISTS events\n" +
				"(\n" +
				"    id INTEGER PRIMARY KEY,\n" +
				"    name TEXT NOT NULL,\n" +
				"    description TEXT,\n" +
				"    'date' TEXT,\n" +
				"    category_id INTEGER\n" +
				");");
		statement.close();
		connection().close();
	}

	ResultSet executeStatement(String query, Map<Integer, String> parameters) throws SQLException {
		ResultSet resultSet;
		PreparedStatement statement;
		statement = connection().prepareStatement(query);
		for (Integer index : parameters.keySet()) {
			statement.setString(index, parameters.get(index));
		}
		resultSet = statement.executeQuery();
		return resultSet;
	}

	ResultSet executeStatement(String query) throws SQLException {
		ResultSet resultSet;
		PreparedStatement statement;
		statement = connection().prepareStatement(query);
		resultSet = statement.executeQuery();
		return resultSet;
	}

	void executeStatementUpdate(String query, Map<Integer, String> parameters) throws SQLException {
		PreparedStatement statement;
		statement = connection().prepareStatement(query);
		for (Integer index : parameters.keySet()) {
			statement.setString(index, parameters.get(index));
		}
		statement.executeUpdate();
		statement.close();
	}

	void executeStatementUpdate(String query) throws SQLException {
		PreparedStatement statement;
		statement = connection().prepareStatement(query);
		statement.executeUpdate();
		statement.close();
	}

}

