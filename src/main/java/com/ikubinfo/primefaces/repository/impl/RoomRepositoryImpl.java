package com.ikubinfo.primefaces.repository.impl;

import java.util.*;

import javax.sql.DataSource;

import com.ikubinfo.primefaces.model.*;
import com.ikubinfo.primefaces.repository.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.ikubinfo.primefaces.repository.RoomRepository;

@Repository
class RoomRepositoryImpl implements RoomRepository {

    Logger logger = LoggerFactory.getLogger(RoomRepositoryImpl.class);

    private static  String GET_ROOMS = "SELECT r.room_id,r.room_name,STRING_AGG (name,',') AS Facilities,r.room_ability_id, r.category_id,  room_name, r.description,beds_number, price, category_name,ability_name, r.updated_on,\n" +
            "\t\t            r.created_on, (ue.first_name || ' ' || ue.last_name) as created_by,\n" +
            "\t\t            CASE WHEN r.updated_by is not null \n" +
            "\t\t            then (select (u.first_name || ' ' || u.last_name) from user_ u where u.user_id=r.updated_by) else '' end as UpdatedBy,\n" +
            "\t\t\t\t\t CASE WHEN STRING_AGG (name,',') is null \n" +
            "\t\t            then '' end \n" +
            "FROM room r\n" +
            " inner join user_ ue on r.created_by=ue.user_id \n" +
            "\tinner join category on r.category_id=category.category_id \n" +
            "\t\tinner join room_ability ra on ra.room_ability_id=r.room_ability_id\n" +
            "\tINNER JOIN facility_room rf ON rf.room_id=r.room_id\n" +
            "\tinner join facility f on f.facility_id=rf.facility_id\n" +
            " where r.is_valid=true ";
    private static final String UPDATE_ROOM = "update room set room_name= :name, description= :description, price= :price, " +
            "beds_number= :bedsNumber, category_id=:category,updated_on=:updatedOn,updated_by=:updatedBy, room_ability_id=:ability where room_id=:id";
    private static final String GET_VACANT_ROOMS = "select r.room_id,r.is_valid,room_name,STRING_AGG (name,',') AS Facilities,r.description, facilities,beds_number, price, category_name ,\n" +
            " CASE WHEN STRING_AGG (name,',') is null \n" +
            "\t\t        then '' end from \n" +
            "\t\t\troom r join room_ability ra on r.room_ability_id= ra.room_ability_id\n" +
            "\t\t\tjoin category on r.category_id=category.category_id\n" +
            "\t\t\tINNER JOIN facility_room rf ON rf.room_id=r.room_id\n" +
            "\t\tinner join facility f on f.facility_id=rf.facility_id\n" +
            "\t\t\twhere ra.code='V' and r.room_id not in (select r.room_id from room r\n" +
            "\t\t\tinner join room_booking br on br.room_id=r.room_id\n" +
            "\t\t\tinner join booking b on b.booking_id=br.booking_id\n" +
            "\t\t\twhere b.check_in >= :firstDate and b.check_in < :secondDate) and r.is_valid=true GROUP by r.room_id, category_name, ability_name\n";
    private static final String DELETE_ROOM = "update room set is_valid= false where room_id=:id";
    private static final String GET_CATEGORIES = "select category_id, category_name from category where is_valid=true";
    private static final String GET_ABILITIES = "select room_ability_id, ability_name from room_ability";
    private static final String GET_ABILITY = "select room_ability_id, ability_name from room_ability where room_ability_id=:id";

    private static final String GET_ROOM = "select room_id, r.room_ability_id, r.category_id,  room_name,  description,beds_number, price, facilities, category_name,ability_name, r.updated_on,\n" +
            "            r.created_on, (ue.first_name || ' ' || ue.last_name) as created_by,\n" +
            "            CASE WHEN r.updated_by is not null \n" +
            "            then (select (u.first_name || ' ' || u.last_name) from user_ u where u.user_id=r.updated_by) else '' end as UpdatedBy\n" +
            "            from room r\n" +
            "            join user_ ue on r.created_by=ue.user_id \n" +
            "\t\t\tjoin category on r.category_id=category.category_id \n" +
            "\t\t\tjoin room_ability ra on ra.room_ability_id=r.room_ability_id\n" +
            "\t\t\twhere r.is_valid=true and room_id=:id";
    private static final String GET_FACILITIES = "select facility_id, name from facility";

    private static final String RESERVED_ROOMS_FOR_BOOKING = "select room.room_id,room_name,description, facilities,beds_number, room.price, category_name from \n" +
            "room join room_booking rb on room.room_id = rb.room_id\n" +
            "join booking on booking.booking_id=rb.booking_id\n" +
            "join category on room.category_id=category.category_id\n" +
            "where rb.booking_id=:id";

    private static final String ROOM_IN_USE = "Select count(room_id) as room_count from room_booking where room_id = ?";


    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertRoomQuery;
    private SimpleJdbcInsert insertFacilityRoom;
    private SimpleJdbcInsert insertPhotoQuery;
    private JdbcTemplate jdbcTemplate;


    @Autowired
    public RoomRepositoryImpl(DataSource datasource) {
        super();
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(datasource);
        this.insertRoomQuery = new SimpleJdbcInsert(datasource).withTableName("room")
                .usingGeneratedKeyColumns("room_id");
        this.insertFacilityRoom = new SimpleJdbcInsert(datasource).withTableName("facility_room")
                .usingGeneratedKeyColumns("facility_room_id");
        this.insertPhotoQuery = new SimpleJdbcInsert(datasource).withTableName("room_photo")
                .usingGeneratedKeyColumns("room_photo_id");
        this.jdbcTemplate = new JdbcTemplate(datasource);
    }

    @Override
    public List<Room> getAll(String name) {
        String queryString = GET_ROOMS;
        Map<String, Object> params = new HashMap<>();
        if (!Objects.isNull(name) && !name.isEmpty()) {
            params.put("name", "%" + name + "%");
            queryString=queryString.concat("and  room_name like  :name ");
        }
        queryString=queryString.concat(" GROUP by r.room_id, category_name, ability_name, (ue.first_name || ' ' || ue.last_name)");

        return namedParameterJdbcTemplate.query(queryString, params, new RoomRowMapper());

    }

    @Override
    public List<Room> getAllVacantRooms(Booking booking) {

        Map<String, Object> params = new HashMap<>();
        params.put("firstDate", booking.getCheckIn());
        params.put("secondDate", booking.getCheckOut());
        return namedParameterJdbcTemplate.query(GET_VACANT_ROOMS, params, new VacantRoomRowMapper());

    }

    @Override
    public List<Room> getReservedRoomsForBooking(int id) {

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return namedParameterJdbcTemplate.query(RESERVED_ROOMS_FOR_BOOKING, params, new VacantRoomRowMapper());
    }


    @Override
    public boolean updateRoom(Room room) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.addValue("name", room.getName());
        namedParameters.addValue("description", room.getDescription());
        namedParameters.addValue("price", room.getPrice());
        namedParameters.addValue("bedsNumber", room.getBedsNumber());
        namedParameters.addValue("category", room.getRoomCategory().getId());
        namedParameters.addValue("ability", room.getRoomAbility().getId());
        namedParameters.addValue("id", room.getId());
        namedParameters.addValue("updatedOn", new Date());
        namedParameters.addValue("updatedBy", room.getUpdatedBy().getId());

        int updatedCount = this.namedParameterJdbcTemplate.update(UPDATE_ROOM, namedParameters);

        return updatedCount > 0;
    }

    @Override
    public boolean create(List<RoomPhoto> photos, Room room) {

        Map<String, Object> parameters = new HashMap<String, Object>();
        Map<String, Object> parameters1 = new HashMap<String, Object>();
        Map<String, Object> parameters2 = new HashMap<String, Object>();

        parameters.put("room_id", room.getId());
        parameters.put("room_name", room.getName());
        parameters.put("description", room.getDescription());
        parameters.put("price", room.getPrice());
        parameters.put("beds_number", room.getBedsNumber());
        parameters.put("category_id", room.getRoomCategory().getId());
        parameters.put("room_ability_id", room.getRoomAbility().getId());
        parameters.put("created_by", room.getCreatedBy().getId());
        parameters.put("created_on", new Date());
        parameters.put("is_valid", "true");
        insertRoomQuery.execute(parameters);
        Number newRoomId = insertRoomQuery.executeAndReturnKey(parameters);

        for (RoomPhoto photo : photos) {
            parameters2.put("room_photo_id", photo.getId());
            parameters2.put("file_name", photo.getName());
            parameters2.put("file_size", photo.getSize());
            parameters2.put("file_type", photo.getType());
            parameters2.put("file_path", photo.getPath());
            parameters2.put("room_id", newRoomId);
            parameters2.put("created_by", room.getCreatedBy().getId());
            parameters2.put("created_on", new Date());
            parameters2.put("is_valid", "true");
            insertPhotoQuery.execute(parameters2);

        }
        for (RoomFacility roomFacility : room.getRoomFacilities()) {
            parameters1.put("facility_id", roomFacility.getId());
            parameters1.put("room_id", newRoomId);
            insertFacilityRoom.execute(parameters1);
        }

        return true;

    }

    @Override
    public void delete(Room room) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.addValue("id", room.getId());

        this.namedParameterJdbcTemplate.update(DELETE_ROOM, namedParameters);

    }

    @Override
    public List<RoomCategory> getCategories() {


        return namedParameterJdbcTemplate.query(GET_CATEGORIES, new RoomCategoryBaseRowMapper());
    }

    @Override
    public List<RoomAbility> getRoomAbilities() {


        return namedParameterJdbcTemplate.query(GET_ABILITIES, new RoomAbilityRowMapper());
    }


    @Override
    public List<RoomFacility> getRoomFacilities() {


        return namedParameterJdbcTemplate.query(GET_FACILITIES, new RoomFacilityRowMapper());
    }


    @Override
    public Room getRoom(int id) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            String queryString = GET_ROOM;

            return namedParameterJdbcTemplate.queryForObject(queryString, params, new RoomRowMapper());

        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }


    @Override
    public boolean isRoomInUse(Room room) {

        return jdbcTemplate.queryForObject(ROOM_IN_USE, Integer.class, room.getId()) > 0;


    }
}
