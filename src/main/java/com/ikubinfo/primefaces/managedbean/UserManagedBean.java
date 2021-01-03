package com.ikubinfo.primefaces.managedbean;


import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.model.User;
import com.ikubinfo.primefaces.service.RoomService;
import com.ikubinfo.primefaces.service.UserService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class UserManagedBean implements Serializable {

    private User user;

    @ManagedProperty(value = "#{userService}")
    private UserService userService;

    @PostConstruct
    public void init() {

        user = userService.getUser(2);

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
}
