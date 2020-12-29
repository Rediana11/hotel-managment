package com.ikubinfo.primefaces.service;

import java.math.BigDecimal;
import java.util.List;

import com.ikubinfo.primefaces.model.Booking;
import com.ikubinfo.primefaces.model.BookingStatus;
import com.ikubinfo.primefaces.model.User;

public interface BookingService {

	List<Booking> getAll();

	List<BookingStatus> getBookingStatuses();

	void delete(Booking booking);

	Booking getBooking (int id);

	boolean save(Booking booking);

	boolean updateBookingStatus(Booking booking);

}