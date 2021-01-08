package com.ikubinfo.primefaces.repository.mapper;

import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.model.RoomPhoto;
import com.ikubinfo.primefaces.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet result, int rowNum) throws SQLException {
        User user = new User();

        user.setId(result.getInt("user_id"));
        user.setFirstName(result.getString("first_name"));
        user.setLastName(result.getString("last_name"));
        user.setUsername(result.getString("username"));
        user.setPassword(result.getString("password_"));
        user.setEmail(result.getString("email"));
        user.setValid(result.getBoolean("is_valid"));

        return  user;
    }
}