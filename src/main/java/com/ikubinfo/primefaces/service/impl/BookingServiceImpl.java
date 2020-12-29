package com.ikubinfo.primefaces.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.ikubinfo.primefaces.model.Booking;
import com.ikubinfo.primefaces.model.BookingStatus;
import org.springframework.stereotype.Service;

import com.ikubinfo.primefaces.model.User;
import com.ikubinfo.primefaces.repository.BookingRepository;
import com.ikubinfo.primefaces.service.BookingService;

@Service("bookingService")
class BookingServiceImpl implements BookingService {

	private BookingRepository bookingRepository;

	public BookingServiceImpl(BookingRepository bookingRepository) {
		super();
		this.bookingRepository = bookingRepository;
	}

	@Override
	public List<Booking> getAll() {

		return bookingRepository.getAll();

	}

	@Override
	public List<BookingStatus> getBookingStatuses() {
		return bookingRepository.getBookingStatuses();
	}

	@Override
	public void delete(Booking booking) {
		bookingRepository.delete(booking);

	}

	@Override
	public Booking getBooking(int id) {
		return bookingRepository.getBooking(id);
	}

	@Override
	public boolean save(Booking booking) {
		return bookingRepository.save(booking);
	}

	@Override
	public boolean updateBookingStatus(Booking booking) {
		return bookingRepository.updateBookingStatus(booking);
	}
}
