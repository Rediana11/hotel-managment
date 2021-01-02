package com.ikubinfo.primefaces.repository.mapper;

import com.ikubinfo.primefaces.model.RoomCategory;
import com.ikubinfo.primefaces.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomCategoryBaseRowMapper implements RowMapper<RoomCategory> {


    @Override
    public RoomCategory mapRow(ResultSet result, int rowNum) throws SQLException {
        RoomCategory roomCategory= new RoomCategory();

        roomCategory.setId(result.getInt("category_id"));
        roomCategory.setName(result.getString("category_name"));
     ;
        return roomCategory;
    }

}
