package com.ikubinfo.primefaces.repository.mapper;

import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.model.RoomCategory;
import com.ikubinfo.primefaces.model.RoomFacility;
import com.ikubinfo.primefaces.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomFacilityRowMapper  implements RowMapper<RoomFacility> {


    @Override
    public RoomFacility mapRow(ResultSet result, int rowNum) throws SQLException {
        RoomFacility roomFacility= new RoomFacility();
        roomFacility.setId(result.getInt("facility_id"));
        roomFacility.setName(result.getString("name"));

        return roomFacility;
    }

}
