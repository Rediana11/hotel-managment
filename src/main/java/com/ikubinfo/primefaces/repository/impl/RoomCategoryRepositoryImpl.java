package com.ikubinfo.primefaces.repository.impl;

import com.ikubinfo.primefaces.model.RoomCategory;
import com.ikubinfo.primefaces.repository.RoomCategoryRepository;
import com.ikubinfo.primefaces.repository.mapper.RoomCategoryRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RoomCategoryRepositoryImpl implements RoomCategoryRepository {
    Logger logger = LoggerFactory.getLogger(RoomRepositoryImpl.class);

    private static final String GET_CATEGORIES = "select category_id,  code, category_name, c.updated_on, \n" +
            "\t\tc.created_on,  (ue.first_name || ' ' || ue.last_name) as created_by,  CASE WHEN c.updated_by is not null \n" +
            "\t\t \t\tthen (select (u.first_name || ' ' || u.last_name) from user_ u where u.user_id=c.updated_by) else '' end as UpdatedBy\n" +
            "from category c\n" +
            "join user_ ue on c.created_by=ue.user_id where c.is_valid=true";
    private static final String UPDATE_CATEGORY = "update category set category_name= :name, code= :code, updated_on=:updatedOn, updated_by=:updatedBy where category_id=:id";
    private static final String DELETE_CATEGORY = "update category set is_valid= false where category_id=:id";
    private static final String GET_CATEGORY = "select category_id,  code,  category_name, c.updated_on,\n" +
            "\t\tc.created_on, (ue.first_name || ' ' || ue.last_name) as created_by,\n" +
            "\t\t CASE WHEN c.updated_by is not null \n" +
            "\t\t \t\tthen (select (u.first_name || ' ' || u.last_name) from user_ u where u.user_id=c.updated_by) else '' end as UpdatedBy\n" +
            "from category c\n" +
            "join user_ ue on c.created_by=ue.user_id where c.is_valid=true and c.category_id=:id";
    private static final String CATEGORY_IN_USE = "Select count(category_id) as category_count from room where category_id = ?";


    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertCategoryQuery;
    private JdbcTemplate jdbcTemplate;


    @Autowired
    public RoomCategoryRepositoryImpl(DataSource datasource) {
        super();
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(datasource);
        this.insertCategoryQuery = new SimpleJdbcInsert(datasource).withTableName("category")
                .usingGeneratedKeyColumns("category_id");
        this.jdbcTemplate = new JdbcTemplate(datasource);
    }

    @Override
    public List<RoomCategory> getAllCategories() {

        return namedParameterJdbcTemplate.query(GET_CATEGORIES, new RoomCategoryRowMapper());
    }

    @Override
    public RoomCategory getRoomCategory(int id) {

        try {
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            return namedParameterJdbcTemplate.queryForObject(GET_CATEGORY, params, new RoomCategoryRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public boolean updateCategory(RoomCategory roomCategory) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.addValue("name", roomCategory.getName());
        namedParameters.addValue("code", roomCategory.getCode());
        namedParameters.addValue("id", roomCategory.getId());
        namedParameters.addValue("updatedOn", new Date());
        namedParameters.addValue("updatedBy", roomCategory.getUpdatedBy().getId());


        int updatedCount = this.namedParameterJdbcTemplate.update(UPDATE_CATEGORY, namedParameters);

        return updatedCount > 0;
    }

    @Override
    public boolean createCategory(RoomCategory roomCategory) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("category_id", roomCategory.getId());
        parameters.put("code", roomCategory.getCode());
        parameters.put("category_name", roomCategory.getName());
        parameters.put("created_by", roomCategory.getCreatedBy().getId());
        parameters.put("created_on", new Date());
        parameters.put("is_valid", "true");

        return insertCategoryQuery.execute(parameters) > 0;
    }

    @Override
    public void deleteCategory(RoomCategory roomCategory) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", roomCategory.getId());
        this.namedParameterJdbcTemplate.update(DELETE_CATEGORY, namedParameters);

    }

    @Override
    public boolean isCategoryInUse(RoomCategory roomCategory) {

        return jdbcTemplate.queryForObject(CATEGORY_IN_USE, Integer.class, roomCategory.getId()) > 0;


    }
}
