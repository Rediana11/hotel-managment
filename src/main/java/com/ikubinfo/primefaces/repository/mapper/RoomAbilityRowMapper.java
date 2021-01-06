package com.ikubinfo.primefaces.repository.mapper;

import com.ikubinfo.primefaces.model.RoomAbility;
import com.ikubinfo.primefaces.model.RoomCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomAbilityRowMapper implements RowMapper<RoomAbility> {

    @Override
    public RoomAbility mapRow(ResultSet result, int rowNum) throws SQLException {
        RoomAbility roomAbility= new RoomAbility();

        roomAbility.setId(result.getInt("room_ability_id"));
        roomAbility.setName(result.getString("ability_name"));
        return  roomAbility;
    }
}
