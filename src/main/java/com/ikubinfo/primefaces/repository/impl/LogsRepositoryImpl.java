package com.ikubinfo.primefaces.repository.impl;

import com.ikubinfo.primefaces.model.Logs;
import com.ikubinfo.primefaces.model.RoomPhoto;
import com.ikubinfo.primefaces.model.User;
import com.ikubinfo.primefaces.repository.LogsRepository;
import com.ikubinfo.primefaces.repository.mapper.LogRowMapper;
import com.ikubinfo.primefaces.repository.mapper.PhotoRowMapper;
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
public class LogsRepositoryImpl implements LogsRepository {


    private static final String GET_LOGS = "select logs_id, logs_name, details, logs.created_on,(u.first_name || ' ' || u.last_name) as created_by from logs \n" +
            "join user_ u on u.user_id = logs.created_by ";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertLogsQuery;

    @Autowired
    public LogsRepositoryImpl(DataSource datasource) {
        super();
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(datasource);
        this.insertLogsQuery = new SimpleJdbcInsert(datasource).withTableName("logs")
                .usingGeneratedKeyColumns("logs_id");
    }

    @Override
    public List<Logs> getLogs() {
        return namedParameterJdbcTemplate.query(GET_LOGS, new LogRowMapper());    }

    @Override
    public boolean addErrorLog (String detail) {

        Logs logs = new Logs();
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("logs_id", 1);
        parameters.put("logs_name","Error");
        parameters.put("details", detail);
        //parameters.put("created_by", room.getCreatedBy());
        parameters.put("created_by", 2); //TODO replace this with line 38
        parameters.put("created_on", new Date());

        return insertLogsQuery.execute(parameters) > 0;

    }

    @Override
    public boolean addSuccessfulLog( String detail) {
        Logs logs = new Logs();
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("logs_id", 1);
        parameters.put("logs_name","Successful");
        parameters.put("details", detail);
        //parameters.put("created_by", room.getCreatedBy());
        parameters.put("created_by", 2); //TODO replace this with line 38
        parameters.put("created_on", new Date());

        return insertLogsQuery.execute(parameters) > 0;

    }
}
