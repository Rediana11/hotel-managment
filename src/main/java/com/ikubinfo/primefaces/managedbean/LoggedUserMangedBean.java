package com.ikubinfo.primefaces.managedbean;

import com.ikubinfo.primefaces.model.User;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class LoggedUserMangedBean implements Serializable {

        private static final long serialVersionUID = 1L;

        private User user;

        public void logOut() {
            user = null;
        }


        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }


}
