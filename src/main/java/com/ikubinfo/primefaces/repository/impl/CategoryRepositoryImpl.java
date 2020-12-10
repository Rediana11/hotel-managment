package com.ikubinfo.primefaces.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.ikubinfo.primefaces.model.Category;
import com.ikubinfo.primefaces.repository.CategoryRepository;
import com.ikubinfo.primefaces.repository.mapper.CategoryRowMapper;

@Repository
class CategoryRepositoryImpl implements CategoryRepository {

	Logger logger = LoggerFactory.getLogger(CategoryRepositoryImpl.class);

	private static final String QUERY = "Select * from public.category where 1=1 ";
	private static final String UPDATE_QUERY = "update category set  name = :name where category_id = :id";
	private static final String CATEGORY_IN_USE = "Select count(category_id) as category_count from film_category where category_id = ?";
	private static final String DELETE_CATEGORY = "Delete from category where category_id = :id ";

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private SimpleJdbcInsert insertCategoryQuery;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public CategoryRepositoryImpl(DataSource datasource) {
		super();
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(datasource);
		this.insertCategoryQuery = new SimpleJdbcInsert(datasource).withTableName("category")
				.usingGeneratedKeyColumns("category_id");
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public List<Category> getAll(String name) {

		Map<String, Object> params = new HashMap<>();
		params.put("name", "%" + name + "%");

		String queryString = QUERY;

		if (!Objects.isNull(name) && !name.isEmpty()) {
			queryString = queryString.concat(" and  category.name like  :name ");
		}

		return namedParameterJdbcTemplate.query(queryString, params, new CategoryRowMapper());

	}

	@Override
	public boolean save(Category category) {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		namedParameters.addValue("name", category.getName());
		namedParameters.addValue("id", category.getId());

		int updatedCount = this.namedParameterJdbcTemplate.update(UPDATE_QUERY, namedParameters);

		return updatedCount > 0;
	}

	@Override
	public boolean create(Category category) {

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", category.getName());
		parameters.put("last_update", category.getLastUpdated());

		return insertCategoryQuery.execute(parameters) > 0;

	}

	@Override
	public boolean isCategoryInUse(Category category) {

		return jdbcTemplate.queryForObject(CATEGORY_IN_USE, Integer.class, category.getId()) > 0;

	}

	@Override
	public void delete(Category category) {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		namedParameters.addValue("id", category.getId());

		this.namedParameterJdbcTemplate.update(DELETE_CATEGORY, namedParameters);

	}
}
