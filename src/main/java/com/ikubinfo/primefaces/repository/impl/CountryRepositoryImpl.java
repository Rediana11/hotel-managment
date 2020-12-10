package com.ikubinfo.primefaces.repository.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ikubinfo.primefaces.model.Country;
import com.ikubinfo.primefaces.repository.CountryRepository;
import com.ikubinfo.primefaces.repository.mapper.CountryRowMapper;

@Repository
class CountryRepositoryImpl implements CountryRepository {
	private static final String UPDATE_QUERY = "update country set  name = :name,continent=:continent  where code = :code";

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public CountryRepositoryImpl(DataSource dataSource) {
		super();
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

	}

	@Override
	public List<Country> getAll(String continent, BigDecimal surface) {

		String queryString = "Select country.code,country.name,country.continent,country.surfacearea,country.indepyear from public.country where 1=1 ";
		Map<String, Object> params = new HashMap<>();
		params.put("continent", "%" + continent + "%");
		params.put("surfaceArea", surface);

		if (!Objects.isNull(continent) && !continent.isEmpty()) {
			queryString = queryString.concat(" AND LOWER(country.continent) LIKE LOWER(:continent) ");

		}

		if (!Objects.isNull(surface)) {
			queryString = queryString.concat("AND country.surfaceArea >= :surfaceArea");

		}
		return namedParameterJdbcTemplate.query(queryString, params, new CountryRowMapper());

	}

	@Override
	public boolean save(Country country) {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		namedParameters.addValue("name", country.getName()).addValue("continent", country.getContinent())
				.addValue("code", country.getCountryCode());

		int updatedCount = this.namedParameterJdbcTemplate.update(UPDATE_QUERY, namedParameters);

		return updatedCount > 0;

	}

}
