package com.ikubinfo.primefaces.repository.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.sql.DataSource;

import com.ikubinfo.primefaces.model.Booking;
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

    private static final String GET_BOOKINGS = "select booking_id,check_in,check_out,persons_number, status_name,ue.username as created_by, u.username as updated_by, price, \n" +
            "b.created_on, b.updated_on from booking b  join booking_status bs on b.booking_status_id=bs.booking_status_id \n" +
            "join user_ u on b.updated_by=u.user_id  join user_ ue on b.created_by=ue.user_id where b.is_valid=true";
    private static final String UPDATE_BOOKING = "update booking set check_out=:date, persons_number=:personsNumber, price=:price where booking_id=:id";
    private static final String CATEGORY_IN_USE = "Select count(category_id) as category_count from film_category where category_id = ?";
    private static final String DELETE_BOOKING = "update booking set is_valid= false where booking_id=:id";
    private static final String UPDATE_STATUS ="update booking set booking_status_id=:statusId where booking_id=:id";

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


        String queryString = GET_BOOKINGS;

        return namedParameterJdbcTemplate.query(queryString, new BookingRowMapper());

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

	/*
	@Override
	public boolean create(Room room) {

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("room_id", room.getId());
		parameters.put("room_name", room.getName());
		parameters.put("description", room.getDescription());
		parameters.put("price", room.getPrice());
		parameters.put("beds_number", room.getBedsNumber());
		parameters.put("category_id", room.getRoomCategory().getId());
		parameters.put("room_ability_id", room.getRoomAbility().getId());
		parameters.put("created_by", room.getCreatedBy());
		parameters.put("created_on", room.getCreatedOn());
		parameters.put("updated_by", room.getUpdatedBy());
		parameters.put("updated_on", room.getUpdatedOn());
		parameters.put("is_valid", room.isValid());

		return insertRoomQuery.execute(parameters) > 0;

	}

	/* @Override
	public boolean isCategoryInUse(Role category) {

		return jdbcTemplate.queryForObject(CATEGORY_IN_USE, Integer.class, category.getId()) > 0;

	}

	 */

    @Override
    public boolean updateBookingStatus(Booking booking) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.addValue("statusId", booking.getBookingStatus());
        namedParameters.addValue("id", booking.getId());

        int updatedCount = this.namedParameterJdbcTemplate.update(UPDATE_STATUS, namedParameters);

        return updatedCount > 0;
    }

    @Override
    public void delete(Booking booking) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.addValue("id", booking.getId());

        this.namedParameterJdbcTemplate.update(DELETE_BOOKING, namedParameters);


    }

}

