package com.ikubinfo.primefaces.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ikubinfo.primefaces.model.User;

public class CountryRowMapper implements RowMapper<User> {
	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User country = new User();

		/* country.setCountryCode(rs.getString("code"));
		country.setName(rs.getString("name"));
		country.setContinent(rs.getString("continent"));
		country.setIndepencenceYear(rs.getInt("indepyear"));

		 */

		return country;
	}
}