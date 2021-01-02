package com.ikubinfo.primefaces.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class User {

	private Integer id;
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private String password;
	private Integer age;
	private List<Role> roles;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", username='" + username + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", age=" + age +
				", roles=" + roles +
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
		if (!(o instanceof User)) return false;
		User user = (User) o;
		return isValid() == user.isValid() &&
				Objects.equals(getId(), user.getId()) &&
				Objects.equals(getFirstName(), user.getFirstName()) &&
				Objects.equals(getLastName(), user.getLastName()) &&
				Objects.equals(getUsername(), user.getUsername()) &&
				Objects.equals(getEmail(), user.getEmail()) &&
				Objects.equals(getPassword(), user.getPassword()) &&
				Objects.equals(getAge(), user.getAge()) &&
				Objects.equals(getRoles(), user.getRoles()) &&
				Objects.equals(getCreatedBy(), user.getCreatedBy()) &&
				Objects.equals(getCreatedOn(), user.getCreatedOn()) &&
				Objects.equals(getUpdatedBy(), user.getUpdatedBy()) &&
				Objects.equals(getUpdatedOn(), user.getUpdatedOn());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getFirstName(), getLastName(), getUsername(), getEmail(), getPassword(), getAge(), getRoles(), getCreatedBy(), getCreatedOn(), getUpdatedBy(), getUpdatedOn(), isValid());
	}
}
