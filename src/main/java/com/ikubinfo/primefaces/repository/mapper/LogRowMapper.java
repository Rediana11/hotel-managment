package com.ikubinfo.primefaces.repository.mapper;

import com.ikubinfo.primefaces.model.Logs;
import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.model.RoomPhoto;
import com.ikubinfo.primefaces.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LogRowMapper implements RowMapper<Logs> {

    @Override
    public Logs mapRow(ResultSet result, int rowNum) throws SQLException {
        Logs log= new Logs();
        User user = new User();
        user.setUsername(result.getString("created_by"));
        log.setId(result.getInt("id"));
        log.setType(result.getString("type"));
        log.setMessage(result.getString("message"));
        log.setLocation_exception(result.getString("location_exception"));
        log.setDetails(result.getString("details"));
        log.setCreatedBy(user);
        log.setCreatedOn(result.getTimestamp("created_on"));

        return  log;
    }

}
