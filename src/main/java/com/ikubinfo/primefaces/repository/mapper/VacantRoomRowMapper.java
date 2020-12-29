package com.ikubinfo.primefaces.repository.mapper;

import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.model.RoomAbility;
import com.ikubinfo.primefaces.model.RoomCategory;
import com.ikubinfo.primefaces.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VacantRoomRowMapper implements RowMapper<Room> {

    @Override
    public Room mapRow(ResultSet result, int rowNum) throws SQLException {
        Room room = new Room();
        RoomCategory category = new RoomCategory();
        room.setId(result.getInt("room_id"));
        room.setName(result.getString("room_name"));
        room.setDescription(result.getString("description"));
        room.setPrice(result.getDouble("price"));
        room.setFacilities(result.getString("facilities"));
        room.setBedsNumber(result.getInt("beds_number"));
        category.setName(result.getString("category_name"));
        room.setRoomCategory(category);
        return room;
    }
}