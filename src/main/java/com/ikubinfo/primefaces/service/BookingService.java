package com.ikubinfo.primefaces.service;

import java.math.BigDecimal;
import java.util.List;

import com.ikubinfo.primefaces.model.Booking;
import com.ikubinfo.primefaces.model.BookingStatus;
import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.model.User;

public interface BookingService {

	List<Booking> getReservedBookings();

	List<Booking> getActiveBookings();

	List<Booking> getCanceledBookings();

	List<BookingStatus> getBookingStatuses();

	void delete(Booking booking);

	Booking getBooking (int id);

	boolean reserve(Booking booking, List<Room> rooms);

	boolean save(Booking booking);

	boolean updateBookingStatusToCheckedIn(Booking booking);

	boolean updateBookingStatusToCheckedOut(Booking booking);


}