package com.ikubinfo.primefaces.managedbean;


import com.ikubinfo.primefaces.model.Client;
import com.ikubinfo.primefaces.model.Role;
import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.model.User;
import com.ikubinfo.primefaces.service.RoomService;
import com.ikubinfo.primefaces.service.UserService;
import com.ikubinfo.primefaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class UserManagedBean implements Serializable {

    private User user;
    private Role role;
    private Client client;
    private String email;


    @ManagedProperty(value = "#{userService}")
    private UserService userService;

    @ManagedProperty(value = "#{messages}")
    private Messages messages;

    @PostConstruct
    public void init() {

        user = userService.getUser(2);
        role =userService.getUserRole(2);
        client = new Client();

    }

    public void getUserByEmail(){
        System.out.println(client + "hola");
        if (! email.equals(client.getEmail()))
        {
            messages.showWarningMessage("Client not found! Create a new Client");
        }
      client= userService.getClientByEmail(email);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Messages getMessages() {
        return messages;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }
}
