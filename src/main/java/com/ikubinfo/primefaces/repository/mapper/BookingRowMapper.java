package com.ikubinfo.primefaces.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.ikubinfo.primefaces.model.Booking;
import com.ikubinfo.primefaces.model.BookingStatus;
import com.ikubinfo.primefaces.model.Client;
import org.springframework.jdbc.core.RowMapper;

import com.ikubinfo.primefaces.model.User;

public class BookingRowMapper implements RowMapper<Booking> {

	@Override
	public Booking mapRow(ResultSet rs, int rowNum) throws SQLException {
		Booking booking = new Booking();
		//booking.getRooms().add(roomRowMapper.mapRow(rs,rowNum));
		User user = new User();
		User user1 = new User();
		Client client = new Client();
		BookingStatus bookingStatus= new BookingStatus();
		client.setEmail(rs.getString("client"));
		booking.setId(rs.getInt("booking_id"));
		booking.setCheckIn(rs.getDate("check_in"));
		booking.setCheckOut(rs.getDate("check_out"));
		booking.setPersonsNumber(rs.getInt("persons_number"));
		bookingStatus.setId(rs.getInt("booking_status_id"));
		bookingStatus.setName(rs.getString("status_name"));
		booking.setRemarks(rs.getString("remarks"));
		booking.setBookingStatus(bookingStatus);
		booking.setPrice(rs.getDouble("price"));
		user.setUsername(rs.getString("created_by"));
		user1.setUsername(rs.getString("updated_by"));
		booking.setCreatedBy(user);
		booking.setUpdatedBy(user1);
		booking.setClient(client);
		booking.setCreatedOn(rs.getDate("created_on"));
		booking.setUpdatedOn(rs.getDate(("updated_on")));

		return booking;
	}
}