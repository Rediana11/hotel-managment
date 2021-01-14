package com.ikubinfo.primefaces.service;

import com.ikubinfo.primefaces.model.Client;
import com.ikubinfo.primefaces.model.Role;
import com.ikubinfo.primefaces.model.User;

import java.util.List;

public interface UserService {

    User getUser(int id);

    Role getUserRole(int id);

    User getLoggedUser(String email, String password);

    Client getClientByEmail(String email);

    boolean insertClient(Client client);

    List<Client> getClients();
}
