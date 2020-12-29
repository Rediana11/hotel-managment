package com.ikubinfo.primefaces.managedbean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;


@ManagedBean
@ViewScoped
public class EmailManagedBean implements Serializable {
        private String sms;
        private String email;
        private String name;
        private String subject;




        public String getSms() {
            return sms;
        }



        public void setSms(String sms) {
            this.sms = sms;
        }



        public String getEmail() {
            return email;
        }



        public void setEmail(String email) {
            this.email = email;
        }



        public String getSubject() {
            return subject;
        }



        public void setSubject(String subject) {
            this.subject = subject;
        }



        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

}
