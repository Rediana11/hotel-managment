package com.ikubinfo.primefaces.service;

import java.math.BigDecimal;
import java.util.List;

import com.ikubinfo.primefaces.model.Booking;
import com.ikubinfo.primefaces.model.User;

public interface BookingService {

	List<Booking> getAll();

	void delete(Booking booking);

	boolean save(Booking booking);

}