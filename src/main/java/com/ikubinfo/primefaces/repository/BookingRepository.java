package com.ikubinfo.primefaces.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ikubinfo.primefaces.model.*;

public interface BookingRepository {

	List<Booking> getReservedBookings(Date checkIn, Date checkOut);

	List<Booking> getActiveBookings(Date checkIn, Date checkOut);

	List<Booking> getCanceledBookings(Date checkIn, Date checkOut);

	Booking getBooking(int id);

	boolean save(Booking booking);

	boolean reserve(Booking booking, List<Room> rooms);

	boolean updateBookingStatusToCheckedIn(Booking booking);

	boolean updateBookingStatusToCheckedOut(Booking booking);

	List<BookingStatus> getBookingStatuses();

	void delete(Booking booking);

}