package dao;

import model.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {


	void add(Category event) throws SQLException;

	Category find(int id) throws SQLException;

	List<Category> getAll() throws SQLException;

}
