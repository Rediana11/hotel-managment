package com.ikubinfo.primefaces.repository;

import java.math.BigDecimal;
import java.util.List;

import com.ikubinfo.primefaces.model.Booking;
import com.ikubinfo.primefaces.model.User;

public interface BookingRepository {

	List<Booking> getAll();

	boolean save(Booking booking);

	boolean updateBookingStatus(Booking booking);

	void delete(Booking booking);

}