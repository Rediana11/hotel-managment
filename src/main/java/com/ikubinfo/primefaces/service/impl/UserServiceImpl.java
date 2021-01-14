package com.ikubinfo.primefaces.service.impl;

import com.ikubinfo.primefaces.model.Client;
import com.ikubinfo.primefaces.model.Role;
import com.ikubinfo.primefaces.model.User;
import com.ikubinfo.primefaces.repository.RoomRepository;
import com.ikubinfo.primefaces.repository.UserRepository;
import com.ikubinfo.primefaces.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(int id) {
        return userRepository.getUser(id);
    }

    @Override
    public Role getUserRole(int id) {
        return userRepository.getUserRole(id);
    }

    @Override
    public User getLoggedUser(String email, String password) {
        return userRepository.getLoggedUser(email,password);
    }

    @Override
    public Client getClientByEmail(String email) {
        return userRepository.getClientByEmail(email);
    }

    @Override
    public boolean insertClient(Client client) {
        return userRepository.insertClient(client);
    }

    @Override
    public List<Client> getClients() {
        return userRepository.getClients();
    }
}
