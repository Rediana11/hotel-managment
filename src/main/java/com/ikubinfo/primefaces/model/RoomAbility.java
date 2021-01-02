package com.ikubinfo.primefaces.model;

import java.util.Date;
import java.util.Objects;

public class RoomAbility {

    private Integer id;
    private String name;
    private String code;
    private User createdBy;
    private Date createdOn;
    private User updatedBy;
    private Date updatedOn;
    private boolean isValid;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    @Override
    public String toString() {
        return "RoomAbility{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", createdBy=" + createdBy +
                ", createdOn=" + createdOn +
                ", updatedBy=" + updatedBy +
                ", updatedOn=" + updatedOn +
                ", isValid=" + isValid +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoomAbility)) return false;
        RoomAbility that = (RoomAbility) o;
        return isValid() == that.isValid() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getCode(), that.getCode()) &&
                Objects.equals(getCreatedBy(), that.getCreatedBy()) &&
                Objects.equals(getCreatedOn(), that.getCreatedOn()) &&
                Objects.equals(getUpdatedBy(), that.getUpdatedBy()) &&
                Objects.equals(getUpdatedOn(), that.getUpdatedOn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCode(), getCreatedBy(), getCreatedOn(), getUpdatedBy(), getUpdatedOn(), isValid());
    }
}
