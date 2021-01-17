package com.ikubinfo.primefaces.managedbean;

import com.ikubinfo.primefaces.model.User;
import com.ikubinfo.primefaces.service.UserService;
import com.ikubinfo.primefaces.util.Messages;
//import org.jasypt.util.password.StrongPasswordEncryptor;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;

@ManagedBean
@RequestScoped
public class LogInManagedBean implements Serializable {

        private String email;
        private String password;

        @ManagedProperty(value = "#{userService}")
        private UserService userService;
        @ManagedProperty(value = "#{loggedUserMangedBean}")
        private LoggedUserMangedBean loggedUserMangedBean;

        @ManagedProperty(value = "#{messages}")
        private Messages messages;

        public String logIn() {
            User user = userService.getLoggedUser(email,password);
            if (user != null) {
                loggedUserMangedBean.setUser(user);
                return "primefaces/booking";

           }
            else {
               messages.showErrorMessage("Email or password is not correct");
            }
            return null;
        }

        public String logOut() {

            loggedUserMangedBean.logOut();
            return "logIn";
        }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public LoggedUserMangedBean getLoggedUserMangedBean() {
        return loggedUserMangedBean;
    }

    public void setLoggedUserMangedBean(LoggedUserMangedBean loggedUserMangedBean) {
        this.loggedUserMangedBean = loggedUserMangedBean;
    }



    public Messages getMessages() {
        return messages;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }
}
