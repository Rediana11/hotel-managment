package com.ikubinfo.primefaces.repository.mapper;

import com.ikubinfo.primefaces.model.Room;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomIdRowMapper implements RowMapper<Room> {

@Override
public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
        Room room = new Room();

        room.setId(rs.getInt("room_id"));

        return room;
        }
        }