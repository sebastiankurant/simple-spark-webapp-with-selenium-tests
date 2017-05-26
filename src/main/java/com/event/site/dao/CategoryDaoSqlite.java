package com.event.site.dao;

import com.event.site.model.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryDaoSqlite extends BaseDao implements CategoryDao {

	private SqliteJDBCConnector sqliteJDBCConnector = new SqliteJDBCConnector();

	@Override
	public void add(Category category) throws SQLException {
		Map<Integer, String> parameters = new HashMap<>();
		parameters.put(1, category.getName());
		this.sqliteJDBCConnector.executeStatementUpdate("INSERT OR IGNORE INTO categories (name) VALUES (?)", parameters);
	}

	@Override
	public Category find(int id) throws SQLException {
		Category category;
		Map<Integer, String> parameters = new HashMap<>();
		parameters.put(1, String.valueOf(id));
		category = this.getCategory(this.sqliteJDBCConnector.executeStatement("SELECT * FROM categories WHERE id=?", parameters));
		return category;
	}

	public Category find(String name) throws SQLException {
		Map<Integer, String> parameters = new HashMap<>();
		parameters.put(1, name);
		Category category;
		category = this.getCategory(this.sqliteJDBCConnector.executeStatement("SELECT * FROM categories WHERE name=?", parameters));
		return category;
	}

	@Override
	public List<Category> getAll() throws SQLException {
		List<Category> categories;
		categories = this.getCategories(this.sqliteJDBCConnector.executeStatement("SELECT * FROM categories"));
		return categories;
	}

	private Category getCategory(ResultSet resultSet) throws SQLException {
		Category category = null;
		if (resultSet.next()) {
			category = new Category(
					resultSet.getString("name")
			);
			category.setId(resultSet.getInt("id"));
		}
		resultSet.close();
		return category;
	}

	private List<Category> getCategories(ResultSet resultSet) throws SQLException {
		List<Category> categories = new ArrayList<>();
		while (resultSet.next()) {
			Category category = new Category(
					resultSet.getString("name")
			);
			category.setId(resultSet.getInt("id"));
			categories.add(category);
		}
		resultSet.close();
		return categories;
	}
}
