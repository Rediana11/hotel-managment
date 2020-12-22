package com.ikubinfo.primefaces.repository.impl;

import com.ikubinfo.primefaces.model.RoomCategory;
import com.ikubinfo.primefaces.repository.RoomCategoryRepository;
import com.ikubinfo.primefaces.repository.mapper.BookingRowMapper;
import com.ikubinfo.primefaces.repository.mapper.RoomCategoryRowMapper;
import com.ikubinfo.primefaces.repository.mapper.RoomRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RoomCategoryRepositoryImpl implements RoomCategoryRepository {
    Logger logger = LoggerFactory.getLogger(RoomRepositoryImpl.class);

    private static final String GET_CATEGORIES = "select category_id, code, category_name, ue.username as created_by, \n" +
            "u.username as updated_by from category r  \n" +
            "\t\t\tjoin user_ u on r.updated_by=u.user_id  join user_ ue on r.created_by=ue.user_id where r.is_valid=true";
    private static final String UPDATE_CATEGORY ="update category set category_name= :name, code= :code where category_id=:id";
    private static final String CATEGORY_IN_USE = "Select count(category_id) as category_count from film_category where category_id = ?";
    private static final String DELETE_CATEGORY = "update category set is_valid= false where category_id=:id";
    private static final String GET_CATEGORY = "select category_id, code, category_name, ue.username as created_by, \n" +
            "u.username as updated_by from category r  \n" +
            "\t\t\tjoin user_ u on r.updated_by=u.user_id  join user_ ue on r.created_by=ue.user_id where r.is_valid=true\n" +
            "\t\t\tand category_id=:id";


    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertRoomQuery;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public RoomCategoryRepositoryImpl(DataSource datasource) {
        super();
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(datasource);
        this.insertRoomQuery = new SimpleJdbcInsert(datasource).withTableName("room")
                .usingGeneratedKeyColumns("room_id");
        this.jdbcTemplate = new JdbcTemplate(datasource);
    }
    @Override
    public List<RoomCategory> getAllCategories() {

        String queryString = GET_CATEGORIES;

        return namedParameterJdbcTemplate.query(queryString, new RoomCategoryRowMapper());
    }

    @Override
    public RoomCategory getRoomCategory(int id) {

        Map<String, Object> params = new HashMap<>();
        params.put("id", id );
        String queryString = GET_CATEGORY;

        return  namedParameterJdbcTemplate.queryForObject(queryString, params, new RoomCategoryRowMapper());
    }

    @Override
    public boolean saveCategory(RoomCategory roomCategory) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.addValue("name", roomCategory.getName());
        namedParameters.addValue("code", roomCategory.getCode());
        namedParameters.addValue("id", roomCategory.getId());

        int updatedCount = this.namedParameterJdbcTemplate.update(UPDATE_CATEGORY, namedParameters);

        return updatedCount > 0;    }

    @Override
    public boolean createCategory(RoomCategory roomCategory) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("category_id", roomCategory.getId());
        parameters.put("code", roomCategory.getCode());
        parameters.put("category_name", roomCategory.getName());
        parameters.put("created_by", roomCategory.getCreatedBy());
        parameters.put("created_on", roomCategory.getCreatedOn());
        parameters.put("updated_by", roomCategory.getUpdatedBy());
        parameters.put("updated_on", roomCategory.getUpdatedOn());
        parameters.put("is_valid", roomCategory.isValid());

        return insertRoomQuery.execute(parameters) > 0;    }

    @Override
    public void deleteCategory(RoomCategory roomCategory) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.addValue("id", roomCategory.getId());

        this.namedParameterJdbcTemplate.update(DELETE_CATEGORY, namedParameters);

    }
}
