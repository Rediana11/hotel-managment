package com.ikubinfo.primefaces.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ikubinfo.primefaces.model.Booking;
import com.ikubinfo.primefaces.model.BookingStatus;
import com.ikubinfo.primefaces.model.Room;
import org.springframework.stereotype.Service;

import com.ikubinfo.primefaces.model.User;
import com.ikubinfo.primefaces.repository.BookingRepository;
import com.ikubinfo.primefaces.service.BookingService;
import org.springframework.transaction.annotation.Transactional;

@Service("bookingService")
class BookingServiceImpl implements BookingService {

	private BookingRepository bookingRepository;

	public BookingServiceImpl(BookingRepository bookingRepository) {
		super();
		this.bookingRepository = bookingRepository;
	}

	@Override
	public List<Booking> getReservedBookings(Date checkIn, Date checkOut) {

		return bookingRepository.getReservedBookings(checkIn,checkOut);

	}

	@Override
	public List<Booking> getActiveBookings(Date checkIn, Date checkOut) {
		return bookingRepository.getActiveBookings(checkIn,checkOut);
	}

	@Override
	public List<Booking> getCanceledBookings(Date checkIn, Date checkOut) {
		return bookingRepository.getCanceledBookings(checkIn,checkOut);
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
	@Transactional
	public boolean reserve(Booking booking,List<Room> rooms) {
		return bookingRepository.reserve(booking,rooms);
	}

	@Override
	public boolean save(Booking booking) {
		return bookingRepository.save(booking);
	}

	@Override
	public boolean updateBookingStatusToCheckedIn(Booking booking) {
		return bookingRepository.updateBookingStatusToCheckedIn(booking);
	}

	@Override
	public boolean updateBookingStatusToCheckedOut(Booking booking) {
		return bookingRepository.updateBookingStatusToCheckedOut(booking);
	}
}
