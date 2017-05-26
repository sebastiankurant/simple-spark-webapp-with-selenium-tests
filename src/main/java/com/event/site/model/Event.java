package com.event.site.model;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Event extends BaseModel {
	private String description;
	private String date;
	private Category category;

	public Event(String name) {
		super(name);
	}

	public static boolean isDateValid(String date) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
			dateFormat.setLenient(false);
			dateFormat.parse(date);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * Gets date.
	 *
	 * @return Value of date.
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Sets new date.
	 *
	 * @param date New value of date.
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Gets description.
	 *
	 * @return Value of description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets new description.
	 *
	 * @param description New value of description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets category.
	 *
	 * @return Value of category.
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * Sets new category.
	 *
	 * @param category New value of category.
	 */
	public void setCategory(Category category) {
		this.category = category;
	}
}
