package com.event.site.dao;

import com.event.site.model.Category;
import com.event.site.model.Event;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventDaoSqlite extends BaseDao implements EventDao {

	private CategoryDaoSqlite categoryDaoSqlite = new CategoryDaoSqlite();
	private SqliteJDBCConnector sqliteJDBCConnector = new SqliteJDBCConnector();

	@Override
	public void add(Event event) throws SQLException {
		Map<Integer, String> parameters = new HashMap<>();
		parameters.put(1, event.getName());
		parameters.put(2, event.getDescription());
		parameters.put(3, event.getDate());
		try {
			parameters.put(4, event.getCategory().getId().toString());
		} catch (NullPointerException e) {
			parameters.put(4, "null");
		}
		this.sqliteJDBCConnector.executeStatementUpdate("INSERT INTO events (name, description, date, category_id) VALUES (?, ?, ?, ?)", parameters);
	}


	@Override
	public Event find(int id) throws SQLException {
		Event event;
		Map<Integer, String> parameters = new HashMap<>();
		parameters.put(1, String.valueOf(id));
		event = this.getEvent(this.sqliteJDBCConnector.executeStatement("SELECT * FROM events WHERE id=?", parameters));
		return event;
	}

	@Override
	public void remove(int id) throws SQLException {
		Map<Integer, String> parameters = new HashMap<>();
		parameters.put(1, String.valueOf(id));
		this.sqliteJDBCConnector.executeStatementUpdate("DELETE FROM events WHERE id=?", parameters);
	}

	@Override
	public void edit(Event event) throws SQLException {
		Map<Integer, String> parameters = new HashMap<>();
		parameters.put(1, event.getName());
		parameters.put(2, event.getDescription());
		parameters.put(3, event.getDate());
		try {
			parameters.put(4, event.getCategory().getId().toString());
		} catch (NullPointerException e) {
			parameters.put(4, "null");
		}
		parameters.put(5, event.getId().toString());
		this.sqliteJDBCConnector.executeStatementUpdate("UPDATE events SET name=? , description=?, date=?, category_id = ? WHERE id=?", parameters);

	}

	@Override
	public List<Event> getAll() throws SQLException {
		List<Event> events;
		events = this.getEvents(this.sqliteJDBCConnector.executeStatement("SELECT * FROM events"));
		return events;
	}

	@Override
	public List<Event> getBy(Category category) throws SQLException {
		List<Event> events = new ArrayList<>();
		Map<Integer, String> parameters = new HashMap<>();
		try {
			parameters.put(1, category.getId().toString());
		} catch (NullPointerException e) {
			return events;
		}
		events = this.getEvents(this.sqliteJDBCConnector.executeStatement("SELECT * FROM events WHERE category_id=?", parameters));
		return events;
	}


	private Event getEvent(ResultSet resultSet) throws SQLException {
		Event event = null;
		if (resultSet.next()) {
			event = new Event(
					resultSet.getString("name")
			);
			event.setId(resultSet.getInt("id"));
			event.setDescription(resultSet.getString("description"));
			event.setDate(resultSet.getString("date"));
			if (resultSet.getInt("category_id") > 0) {
				event.setCategory(categoryDaoSqlite.find(resultSet.getInt("category_id")));
			}
		}
		resultSet.close();
		return event;
	}

	private List<Event> getEvents(ResultSet resultSet) throws SQLException {
		List<Event> events = new ArrayList<>();
		while (resultSet.next()) {
			Event event = new Event(
					resultSet.getString("name")
			);
			event.setId(resultSet.getInt("id"));
			event.setDescription(resultSet.getString("description"));
			event.setDate(resultSet.getString("date"));
			if (resultSet.getInt("category_id") > 0) {
				event.setCategory(categoryDaoSqlite.find(resultSet.getInt("category_id")));
			}
			events.add(event);
		}
		resultSet.close();
		return events;
	}
}
