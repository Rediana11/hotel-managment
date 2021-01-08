package com.ikubinfo.primefaces.repository.mapper;

import com.ikubinfo.primefaces.model.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VacantRoomRowMapper implements RowMapper<Room> {

    @Override
    public Room mapRow(ResultSet result, int rowNum) throws SQLException {
        Room room = new Room();
        RoomCategory category = new RoomCategory();
        RoomFacility roomFacility = new RoomFacility();
        List<RoomFacility> facilities = new ArrayList<RoomFacility>();
        room.setId(result.getInt("room_id"));
        room.setName(result.getString("room_name"));
        room.setDescription(result.getString("description"));
        room.setPrice(result.getDouble("price"));
        roomFacility.setName(result.getString("facilities"));

        room.setFacilities(result.getString("facilities"));
        room.setBedsNumber(result.getInt("beds_number"));
        category.setName(result.getString("category_name"));
        room.setRoomCategory(category);
        facilities.add(roomFacility);
        room.setRoomFacilities(facilities);
        return room;
    }
}