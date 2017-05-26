package dao;

import model.Category;
import model.Event;

import java.sql.SQLException;
import java.util.List;

public interface EventDao {

	void add(Event event) throws SQLException;

	Event find(int id) throws SQLException;

	void remove(int id) throws SQLException;

	void edit(Event event) throws SQLException;

	List<Event> getAll() throws SQLException;

	List<Event> getBy(Category category) throws SQLException;
}
