package com.ikubinfo.primefaces.repository.mapper;

import com.ikubinfo.primefaces.model.BookingStatus;
import com.ikubinfo.primefaces.model.RoomAbility;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingStatusRowMapper implements RowMapper<BookingStatus> {

    @Override
    public BookingStatus mapRow(ResultSet result, int rowNum) throws SQLException {
        BookingStatus bookingStatus= new BookingStatus();

        bookingStatus.setId(result.getInt("booking_status_id"));
        bookingStatus.setName(result.getString("status_name"));
        return  bookingStatus;
    }
}