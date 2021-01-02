package com.ikubinfo.primefaces.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Role {

	private Integer id;
	private String name;
	private String description;
	private List<User> users;
	private User createdBy;
	private Date createdOn;
	private User updatedBy;
	private Date updatedOn;
	private boolean isValid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean valid) {
		isValid = valid;
	}

	@Override
	public String toString() {
		return "Role{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", users=" + users +
				", createdBy=" + createdBy +
				", createdOn=" + createdOn +
				", updatedBy=" + updatedBy +
				", updatedOn=" + updatedOn +
				", isValid=" + isValid +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Role)) return false;
		Role role = (Role) o;
		return isValid() == role.isValid() &&
				Objects.equals(getId(), role.getId()) &&
				Objects.equals(getName(), role.getName()) &&
				Objects.equals(getDescription(), role.getDescription()) &&
				Objects.equals(getUsers(), role.getUsers()) &&
				Objects.equals(getCreatedBy(), role.getCreatedBy()) &&
				Objects.equals(getCreatedOn(), role.getCreatedOn()) &&
				Objects.equals(getUpdatedBy(), role.getUpdatedBy()) &&
				Objects.equals(getUpdatedOn(), role.getUpdatedOn());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getName(), getDescription(), getUsers(), getCreatedBy(), getCreatedOn(), getUpdatedBy(), getUpdatedOn(), isValid());
	}
}
