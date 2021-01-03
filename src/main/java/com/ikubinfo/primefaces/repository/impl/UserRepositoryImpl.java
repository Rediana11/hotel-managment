package com.ikubinfo.primefaces.repository.impl;

import com.ikubinfo.primefaces.model.RoomPhoto;
import com.ikubinfo.primefaces.model.User;
import com.ikubinfo.primefaces.repository.UserRepository;
import com.ikubinfo.primefaces.repository.mapper.PhotoRowMapper;
import com.ikubinfo.primefaces.repository.mapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {


    private static final String GET_USER = "select user_id,first_name, last_name,age, email  from user_ where user_id=:id";


    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public UserRepositoryImpl(DataSource datasource) {
        super();
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(datasource);

    }

    @Override
    public User getUser(int id) {

        Map<String, Object> params = new HashMap<>();
        params.put("id", id );

        return  namedParameterJdbcTemplate.queryForObject(GET_USER, params, new UserRowMapper());

    }

}
