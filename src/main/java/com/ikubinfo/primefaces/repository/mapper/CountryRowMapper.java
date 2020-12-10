package com.ikubinfo.primefaces.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ikubinfo.primefaces.model.Country;

public class CountryRowMapper implements RowMapper<Country> {
	@Override
	public Country mapRow(ResultSet rs, int rowNum) throws SQLException {
		Country country = new Country();

		country.setCountryCode(rs.getString("code"));
		country.setName(rs.getString("name"));
		country.setContinent(rs.getString("continent"));
		country.setIndepencenceYear(rs.getInt("indepyear"));

		return country;
	}
}