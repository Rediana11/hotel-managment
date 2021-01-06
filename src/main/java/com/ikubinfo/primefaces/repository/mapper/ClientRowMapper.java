package com.ikubinfo.primefaces.repository.mapper;

import com.ikubinfo.primefaces.model.Client;
import com.ikubinfo.primefaces.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientRowMapper implements RowMapper<Client> {
    @Override
    public Client mapRow(ResultSet result, int rowNum) throws SQLException {
        Client client = new Client();

        client.setId(result.getInt("client_id"));
        client.setFirstName(result.getString("first_name"));
        client.setLastName(result.getString("last_name"));
        client.setAge(result.getInt("age"));
        client.setEmail(result.getString("email"));

        return  client;
    }
}
