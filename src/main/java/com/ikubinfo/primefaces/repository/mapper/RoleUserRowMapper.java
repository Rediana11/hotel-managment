package com.ikubinfo.primefaces.repository.mapper;

import com.ikubinfo.primefaces.model.Role;
import com.ikubinfo.primefaces.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleUserRowMapper implements RowMapper<Role> {

    @Override
    public Role mapRow(ResultSet result, int rowNum) throws SQLException {
        Role user = new Role();

        user.setId(result.getInt("role_id"));
        user.setName(result.getString("role_name"));

        return  user;
    }

}
