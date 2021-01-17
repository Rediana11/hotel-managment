package com.ikubinfo.primefaces.model;

import java.util.Date;

public class Logs {

    private Integer id;
    private String name;
    private String detail;
    private User createdBy;
    private Date createdOn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String details) {
        this.detail = details;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        return "Logs{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", details='" + detail + '\'' +
                ", createdBy=" + createdBy +
                ", createdOn=" + createdOn +
                '}';
    }
}
