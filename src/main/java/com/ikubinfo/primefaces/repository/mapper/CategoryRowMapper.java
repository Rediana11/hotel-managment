package com.ikubinfo.primefaces.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.ikubinfo.primefaces.model.Role;

public class CategoryRowMapper implements RowMapper<Role> {

	@Override
	public Role mapRow(ResultSet result, int rowNum) throws SQLException {
		Role category = new Role();
		category.setId(result.getInt("category_id"));
		category.setName(result.getString("name"));
		category.setLastUpdated(new Date(result.getTimestamp("last_update").getTime()));
		return category;
	}

}
