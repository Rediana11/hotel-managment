package com.ikubinfo.primefaces.repository.impl;

import com.ikubinfo.primefaces.config.AppConfiguration;
import com.ikubinfo.primefaces.model.Logs;
import com.ikubinfo.primefaces.repository.LogsRepository;
import com.ikubinfo.primefaces.repository.mapper.LogRowMapper;
import com.ikubinfo.primefaces.repository.mapper.PhotoRowMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LogsRepositoryImpl implements LogsRepository {


    private static final String GET_LOGS = "select id, type, message,location_exception, details,log.created_on,(u.first_name || ' ' || u.last_name) as created_by from log\n" +
            "            join user_ u on u.user_id = log.created_by ";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertLogsQuery;
    private  List<Logs> logs;
    @Autowired
    public LogsRepositoryImpl() {
        HikariConfig config = new HikariConfig();
		config.setDriverClassName(org.postgresql.Driver.class.getName());
		config.setJdbcUrl("jdbc:postgresql://localhost:5432/hotel_managment");
		config.setUsername("postgres");
		config.setPassword("postgre");
        try(HikariDataSource datasource = new HikariDataSource(config);Connection connection = datasource.getConnection()){
            this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(datasource);
            this.insertLogsQuery = new SimpleJdbcInsert(datasource).withTableName("log")
                    .usingGeneratedKeyColumns("id");
           logs= namedParameterJdbcTemplate.query(GET_LOGS, new LogRowMapper());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Override
    public List<Logs> getLogs() {
        return logs;
    }

    @Override
    public boolean addErrorLog (Logs log) {

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("id", log.getId());
        parameters.put("type","Error");
        parameters.put("message", log.getMessage());
        parameters.put("location_exception",log.getLocation_exception());
        parameters.put("details", log.getDetails());
        parameters.put("created_by", 2);
        parameters.put("created_on", new Date());

        return insertLogsQuery.execute(parameters) > 0;

    }

}
