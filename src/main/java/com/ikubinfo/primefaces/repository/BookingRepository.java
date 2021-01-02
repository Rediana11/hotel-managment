package com.ikubinfo.primefaces.repository;

import java.math.BigDecimal;
import java.util.List;

import com.ikubinfo.primefaces.model.Booking;
import com.ikubinfo.primefaces.model.BookingStatus;
import com.ikubinfo.primefaces.model.RoomAbility;
import com.ikubinfo.primefaces.model.User;

public interface BookingRepository {

	List<Booking> getAll();

	Booking getBooking(int id);

	boolean save(Booking booking);

	boolean reserve(Booking booking);

	boolean updateBookingStatus(Booking booking);

	List<BookingStatus> getBookingStatuses();

	void delete(Booking booking);

}