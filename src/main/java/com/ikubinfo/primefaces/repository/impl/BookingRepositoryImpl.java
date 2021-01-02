package com.ikubinfo.primefaces.repository.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.sql.DataSource;

import com.ikubinfo.primefaces.model.Booking;
import com.ikubinfo.primefaces.model.BookingStatus;
import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.repository.mapper.BookingStatusRowMapper;
import com.ikubinfo.primefaces.repository.mapper.RoomRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.ikubinfo.primefaces.model.User;
import com.ikubinfo.primefaces.repository.BookingRepository;
import com.ikubinfo.primefaces.repository.mapper.BookingRowMapper;

@Repository
class BookingRepositoryImpl implements BookingRepository {


    Logger logger = LoggerFactory.getLogger(BookingRepositoryImpl.class);

    private static final String GET_BOOKINGS = "select booking_id,bs.booking_status_id,check_in,check_out,persons_number, status_name,ue.username as created_by, u.username as updated_by, price, \n" +
            "b.created_on, b.updated_on from booking b  join booking_status bs on b.booking_status_id=bs.booking_status_id \n" +
            "join user_ u on b.updated_by=u.user_id  join user_ ue on b.created_by=ue.user_id where b.is_valid=true";
    private static final String GET_BOOKING = "select booking_id,check_in,check_out,persons_number, status_name,ue.username as created_by, u.username as updated_by, price, \n" +
            "b.created_on, b.updated_on from booking b  join booking_status bs on b.booking_status_id=bs.booking_status_id \n" +
            "join user_ u on b.updated_by=u.user_id  join user_ ue on b.created_by=ue.user_id where b.is_valid=true and booking_id=:id";
    private static final String UPDATE_BOOKING = "update booking set check_out=:date, persons_number=:personsNumber, price=:price where booking_id=:id";
    private static final String CATEGORY_IN_USE = "Select count(category_id) as category_count from film_category where category_id = ?";
    private static final String DELETE_BOOKING = "update booking set is_valid= false where booking_id=:id";
    private static final String UPDATE_STATUS ="update booking set booking_status_id=:statusId where booking_id=:id";
    private static final String GET_BOOKING_STATUSES = "select booking_status_id, status_name from booking_status";


    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertRoomQuery;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookingRepositoryImpl(DataSource datasource) {
        super();
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(datasource);
        this.insertRoomQuery = new SimpleJdbcInsert(datasource).withTableName("booking")
                .usingGeneratedKeyColumns("booking_id");
        this.jdbcTemplate = new JdbcTemplate(datasource);
    }

    @Override
    public List<Booking> getAll() {


        return namedParameterJdbcTemplate.query(GET_BOOKINGS, new BookingRowMapper());

    }

    @Override
    public Booking getBooking(int id) {

        Map<String, Object> params = new HashMap<>();
        params.put("id", id );

        return  namedParameterJdbcTemplate.queryForObject(GET_BOOKING, params, new BookingRowMapper());

    }


	@Override
	public boolean save(Booking booking) {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		namedParameters.addValue("date", booking.getCheckOut());
		namedParameters.addValue("personsNumber", booking.getPersonsNumber());
		namedParameters.addValue("price", booking.getPrice());
		namedParameters.addValue("id", booking.getId());

		int updatedCount = this.namedParameterJdbcTemplate.update(UPDATE_BOOKING, namedParameters);

		return updatedCount > 0;
	}

    @Override
    public boolean reserve(Booking booking) {
        String insertBooking = "insert into booking  values (default, :checkIn, :checkOut, :personsNumber, 1, 2, current_timestamp, null, null, true, :price );";
        String insertRoomBooking = "insert into room_booking  values (default, (select max(booking_id) from booking),  :roomId)";

        MapSqlParameterSource namedParameters1 = new MapSqlParameterSource();
        MapSqlParameterSource namedParameters2 = new MapSqlParameterSource();

        namedParameters1.addValue("checkIn", booking.getCheckIn());
        namedParameters1.addValue("checkOut", booking.getCheckOut());
        namedParameters1.addValue("personsNumber", booking.getPersonsNumber());
        namedParameters1.addValue("personsNumber", booking.getPrice());
        namedParameters2.addValue("roomId", booking.getRooms());
        int insertedCount = this.namedParameterJdbcTemplate.update(insertBooking, namedParameters1);
        int insertedCount2 = this.namedParameterJdbcTemplate.update(insertRoomBooking, namedParameters2);

        return insertedCount + insertedCount2 >1;

    }

	/* @Override
	public boolean isCategoryInUse(Role category) {

		return jdbcTemplate.queryForObject(CATEGORY_IN_USE, Integer.class, category.getId()) > 0;

	}

	 */

    @Override
    public boolean updateBookingStatus(Booking booking) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.addValue("id", booking.getId());

        namedParameters.addValue("statusId", booking.getBookingStatus().getId());

        int updatedCount = this.namedParameterJdbcTemplate.update(UPDATE_STATUS, namedParameters);

        return updatedCount > 0;
    }

    @Override
    public List<BookingStatus> getBookingStatuses() {
        return namedParameterJdbcTemplate.query(GET_BOOKING_STATUSES, new BookingStatusRowMapper());    }

    @Override
    public void delete(Booking booking) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.addValue("id", booking.getId());

        this.namedParameterJdbcTemplate.update(DELETE_BOOKING, namedParameters);


    }

}

