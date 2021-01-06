package com.ikubinfo.primefaces.repository.mapper;

import com.ikubinfo.primefaces.model.RoomCategory;

import java.sql.ResultSet;

import com.ikubinfo.primefaces.model.User;
import org.springframework.jdbc.core.RowMapper;
import java.sql.SQLException;

public class RoomCategoryRowMapper implements RowMapper<RoomCategory> {


    @Override
    public RoomCategory mapRow(ResultSet result, int rowNum) throws SQLException {
        RoomCategory roomCategory= new RoomCategory();
        User userCreated = new User();
        User userUpdated = new User();
        userCreated.setUsername(result.getString("created_by"));
        userUpdated.setUsername(result.getString("updatedby"));
        roomCategory.setId(result.getInt("category_id"));
        roomCategory.setName(result.getString("category_name"));
        roomCategory.setCode(result.getString("code"));
        roomCategory.setCreatedOn(result.getDate("created_on"));
        roomCategory.setUpdatedOn(result.getTimestamp("updated_on"));
        roomCategory.setCreatedBy(userCreated);
        roomCategory.setUpdatedBy(userUpdated);
        return roomCategory;
    }

}
