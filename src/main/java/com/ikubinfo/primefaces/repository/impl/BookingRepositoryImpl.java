package com.ikubinfo.primefaces.repository.impl;

import java.math.BigDecimal;
import java.util.*;

import javax.sql.DataSource;
import javax.swing.*;
import javax.xml.transform.Result;

import com.ikubinfo.primefaces.model.Booking;
import com.ikubinfo.primefaces.model.BookingStatus;
import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.repository.mapper.*;
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

@Repository
class BookingRepositoryImpl implements BookingRepository {


    Logger logger = LoggerFactory.getLogger(BookingRepositoryImpl.class);

    private static final String GET_RESERVED_BOOKINGS = "select  booking_id,bs.booking_status_id,check_in,b.is_valid,check_out,price,b.updated_on,b.created_on,persons_number,status_name remarks,status_name, \n" +
            "\t\t (ue.first_name || ' ' || ue.last_name) as created_by,\n" +
            "\t\t\t            CASE WHEN b.updated_by is not null \n" +
            "\t\t\t            then (select (u.first_name || ' ' || u.last_name) from user_ u where u.user_id=b.updated_by) else '' end as updated_by\n" +
            "\t\t\t           from booking b join user_ ue on b.created_by=ue.user_id \n" +
            "\t\t\tjoin booking_status bs on b.booking_status_id=bs.booking_status_id \n" +
            "\t\t\twhere b.is_valid=true and b.booking_status_id=1 \n";
    private static final String GET_ACTIVE_BOOKINGS = "select  booking_id,b.is_valid,bs.booking_status_id,check_in,check_out,price,b.updated_on,b.created_on,persons_number,status_name remarks,status_name, \n" +
            "\t\t (ue.first_name || ' ' || ue.last_name) as created_by,\n" +
            "\t\t\t            CASE WHEN b.updated_by is not null \n" +
            "\t\t\t            then (select (u.first_name || ' ' || u.last_name) from user_ u where u.user_id=b.updated_by) else '' end as updated_by\n" +
            "\t\t\t           from booking b join user_ ue on b.created_by=ue.user_id \n" +
            "\t\t\tjoin booking_status bs on b.booking_status_id=bs.booking_status_id \n" +
            "\t\t\twhere b.is_valid=true and b.booking_status_id=3";
    private static final String GET_CANCELED_BOOKINGS="select  booking_id,bs.booking_status_id,check_in,check_out,price,b.updated_on,b.created_on,b.is_valid,persons_number,status_name remarks,status_name,\n" +
            "                    (ue.first_name || ' ' || ue.last_name) as created_by,\n" +
            "                       CASE WHEN b.updated_by is not null\n" +
            "               then (select (u.first_name || ' ' || u.last_name) from user_ u where u.user_id=b.updated_by) else '' end as updated_by\n" +
            "                        from booking b join user_ ue on b.created_by=ue.user_id\n" +
            "                  join booking_status bs on b.booking_status_id=bs.booking_status_id \n" +
            "               where  b.is_valid=false and b.booking_status_id=2";

    private static final String GET_BOOKING = "select  booking_id,bs.booking_status_id,check_in,check_out,price,b.created_on,b.updated_on," +
            "persons_number,status_name remarks,status_name, \n" +
            "\t\t (ue.first_name || ' ' || ue.last_name) as created_by,\n" +
            "\t\t\t            CASE WHEN b.updated_by is not null \n" +
            "\t\t\t            then (select (u.first_name || ' ' || u.last_name) from user_ u where u.user_id=b.updated_by) else '' end as updated_by\n" +
            "\t\t\t           from booking b join user_ ue on b.created_by=ue.user_id \n" +
            "\t\t\tjoin booking_status bs on b.booking_status_id=bs.booking_status_id \n" +
            "\t\t\twhere b.is_valid=true and booking_id=:id";
    private static final String UPDATE_BOOKING = "update booking set check_out=:date, persons_number=:personsNumber, price=:price where booking_id=:id";
    private static final String CATEGORY_IN_USE = "Select count(category_id) as category_count from film_category where category_id = ?";
    private static final String DELETE_BOOKING = "update booking set is_valid= false where booking_id=:id";
    private static final String UPDATE_STATUS_CHECK_IN ="update booking set booking_status_id=:statusId where booking_id=:id";
    private static final String UPDATE_STATUS_CHECK_OUT ="update booking set booking_status_id=:statusId where booking_id=:id";
    private static final String GET_BOOKING_STATUSES = "select booking_status_id, status_name from booking_status";
    private static  String GET_ROOM_ABILITY_ID ="select room_ability_id from room_ability where code= ";
    private static String UPDATE_ROOM_ABILITY = "update room \n" +
            "set  room_ability_id =:abilityId from  room_booking rb  join booking b on b.booking_id=rb.booking_id where  b.booking_id=:bookingId";
    private static final String GET_MAX_BOOKING_ID = "select max(booking_id) as booking_id from booking";


    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertBookingQuery;
    private SimpleJdbcInsert insertRoomBookingQuery;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookingRepositoryImpl(DataSource datasource) {
        super();
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(datasource);
        this.insertBookingQuery = new SimpleJdbcInsert(datasource).withTableName("booking")
                .usingGeneratedKeyColumns("booking_id");
        this.insertRoomBookingQuery = new SimpleJdbcInsert(datasource).withTableName("room_booking")
                .usingGeneratedKeyColumns("room_booking_id");
        this.jdbcTemplate = new JdbcTemplate(datasource);
    }

    @Override
    public List<Booking> getReservedBookings() {


        return namedParameterJdbcTemplate.query(GET_RESERVED_BOOKINGS, new BookingRowMapper());

    }

    @Override
    public List<Booking> getActiveBookings() {


        return namedParameterJdbcTemplate.query(GET_ACTIVE_BOOKINGS, new BookingRowMapper());

    }

    @Override
    public List<Booking> getCanceledBookings() {


        return namedParameterJdbcTemplate.query(GET_CANCELED_BOOKINGS, new BookingRowMapper());

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
    public boolean reserve(Booking booking,List<Room> rooms) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        Map<String, Object> parameters1 = new HashMap<String, Object>();

        parameters.put("check_in", booking.getCheckIn());
        parameters.put("check_out", booking.getCheckOut());
        parameters.put("persons_number", booking.getPersonsNumber());
        parameters.put("booking_status_id",1);
        parameters.put("created_by", 2); //TODO replace this with current user
        parameters.put("created_on", new Date());
        parameters.put("price", booking.getPrice());
        parameters.put("is_valid","true");
        insertBookingQuery.execute(parameters);
        for (int i=0;i<rooms.size();i++){
            int roomId=rooms.get(i).getId();
            parameters1.put("room_id", roomId);
            parameters1.put("booking_id", getMaxBookingId());
        }
        return insertRoomBookingQuery.execute(parameters1)>0;

    }

    private int getMaxBookingId() {
         return jdbcTemplate.queryForObject(GET_MAX_BOOKING_ID,new BookingIdRowMaper()).getId();
    }


    @Override
    public boolean updateBookingStatusToCheckedIn(Booking booking) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.addValue("id", booking.getId());

        namedParameters.addValue("statusId", 3);


        int updatedCount = this.namedParameterJdbcTemplate.update(UPDATE_STATUS_CHECK_IN, namedParameters);

        return updatedCount > 0;
    }

    @Override
    public boolean updateBookingStatusToCheckedOut(Booking booking) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.addValue("id", booking.getId());

        namedParameters.addValue("statusId", 5);


        int updatedCount = this.namedParameterJdbcTemplate.update(UPDATE_STATUS_CHECK_OUT, namedParameters);

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

