package com.ikubinfo.primefaces.repository.mapper;

import com.ikubinfo.primefaces.model.Client;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientIdRowMapper implements RowMapper<Client> {
    @Override
    public Client mapRow(ResultSet result, int rowNum) throws SQLException {
        Client client = new Client();

        client.setId(result.getInt("client_id"));

        return  client;
    }
}
