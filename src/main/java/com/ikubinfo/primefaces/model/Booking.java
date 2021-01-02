package com.ikubinfo.primefaces.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Booking {

	private Integer id;
	private Date checkIn;
	private Date checkOut;
	private Integer personsNumber;
	private double price;
	private BookingStatus bookingStatus;
	private List<User> users;
	private List<Room> rooms;
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

	public Date getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}

	public Date getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}

	public Integer getPersonsNumber() {
		return personsNumber;
	}

	public void setPersonsNumber(Integer personsNumber) {
		this.personsNumber = personsNumber;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public BookingStatus getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(BookingStatus bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
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
		return "Booking{" +
				"id=" + id +
				", checkIn=" + checkIn +
				", checkOut=" + checkOut +
				", personsNumber=" + personsNumber +
				", price=" + price +
				", bookingStatus=" + bookingStatus +
				", users=" + users +
				", rooms=" + rooms +
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
		if (!(o instanceof Booking)) return false;
		Booking booking = (Booking) o;
		return Double.compare(booking.getPrice(), getPrice()) == 0 &&
				isValid() == booking.isValid() &&
				Objects.equals(getId(), booking.getId()) &&
				Objects.equals(getCheckIn(), booking.getCheckIn()) &&
				Objects.equals(getCheckOut(), booking.getCheckOut()) &&
				Objects.equals(getPersonsNumber(), booking.getPersonsNumber()) &&
				Objects.equals(getBookingStatus(), booking.getBookingStatus()) &&
				Objects.equals(getUsers(), booking.getUsers()) &&
				Objects.equals(getRooms(), booking.getRooms()) &&
				Objects.equals(getCreatedBy(), booking.getCreatedBy()) &&
				Objects.equals(getCreatedOn(), booking.getCreatedOn()) &&
				Objects.equals(getUpdatedBy(), booking.getUpdatedBy()) &&
				Objects.equals(getUpdatedOn(), booking.getUpdatedOn());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getCheckIn(), getCheckOut(), getPersonsNumber(), getPrice(), getBookingStatus(), getUsers(), getRooms(), getCreatedBy(), getCreatedOn(), getUpdatedBy(), getUpdatedOn(), isValid());
	}
}
