package com.ikubinfo.primefaces.repository.impl;

import com.ikubinfo.primefaces.model.*;
import com.ikubinfo.primefaces.repository.UserRepository;
import com.ikubinfo.primefaces.repository.mapper.ClientRowMapper;
import com.ikubinfo.primefaces.repository.mapper.PhotoRowMapper;
import com.ikubinfo.primefaces.repository.mapper.RoleUserRowMapper;
import com.ikubinfo.primefaces.repository.mapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {


    private static final String GET_USER = "select user_id,first_name, last_name,age, email  from user_ where user_id=:id";

    private static final String GET_USER_ROLE = "select role_.role_id, role_name \n" +
            "from user_ join role_user rl on rl.user_id=user_.user_id join role_ on rl.role_id = role_.role_id\n" +
            "where user_.user_id=:id";
    private static final String GET_CLIENT_BY_EMAIL= "select * from client where email=:email";

    private static final String GET_CLIENTS="select * from client";



    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertClientQuery;


    @Autowired
    public UserRepositoryImpl(DataSource datasource) {
        super();
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(datasource);
        this.insertClientQuery = new SimpleJdbcInsert(datasource).withTableName("client")
                .usingGeneratedKeyColumns("client_id");
        this.jdbcTemplate = new JdbcTemplate(datasource);
    }

    @Override
    public User getUser(int id) {

        Map<String, Object> params = new HashMap<>();
        params.put("id", id );

        return  namedParameterJdbcTemplate.queryForObject(GET_USER, params, new UserRowMapper());

    }

    @Override
    public Role getUserRole(int id) {

        Map<String, Object> params = new HashMap<>();
        params.put("id", id );

        return  namedParameterJdbcTemplate.queryForObject(GET_USER_ROLE, params, new RoleUserRowMapper());

    }

    @Override
    public Client getClientByEmail(String email) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", email );

        return  namedParameterJdbcTemplate.queryForObject(GET_CLIENT_BY_EMAIL, params, new ClientRowMapper());
    }

    @Override
    public List<Client> getClients() {

        return  namedParameterJdbcTemplate.query(GET_CLIENTS, new ClientRowMapper());
    }

    @Override
    public boolean insertClient(Client client) {

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("client_id", client.getId());
        parameters.put("first_name", client.getFirstName());
        parameters.put("last_name", client.getLastName());
        parameters.put("email", client.getEmail());
        parameters.put("age", client.getAge());

        return insertClientQuery.execute(parameters) > 0;

    }

}
