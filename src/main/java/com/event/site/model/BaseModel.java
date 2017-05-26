package com.event.site.model;

import java.lang.reflect.Field;

public class BaseModel {

	protected Integer id;
	protected String name;

	BaseModel(String name) {
		this.name = name;
	}

	/**
	 * Gets name.
	 *
	 * @return Value of name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets new name.
	 *
	 * @param name New value of name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets id.
	 *
	 * @return Value of id.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets new id.
	 *
	 * @param id New value of id.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (Field field : this.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			Object value = null;
			try {
				value = field.get(this);
				if (value != null) {
					sb.append(field.getName() + ":" + value + ",");
				}
			} catch (IllegalAccessException e) {
				System.out.println("Can't access object.");
			}
		}
		return sb.toString();
	}
}
