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
        log.setId(result.getInt("logs_id"));
        log.setName(result.getString("logs_name"));
        log.setDetail(result.getString("details"));
        log.setCreatedBy(user);
        log.setCreatedOn(result.getDate("created_on"));

        return  log;
    }

}
