package com.ikubinfo.primefaces.model;

import java.util.Date;
import java.util.Objects;

public class RoomPhoto {

    private Integer id;
    private String name;
    private String type;
    private String size;
    private String path;
    private Room room;
    private User createdBy;
    private Date createdOn;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
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

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "RoomPhoto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", size='" + size + '\'' +
                ", path='" + path + '\'' +
                ", room=" + room +
                ", createdBy=" + createdBy +
                ", createdOn=" + createdOn +
                ", isValid=" + isValid +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoomPhoto)) return false;
        RoomPhoto photo = (RoomPhoto) o;
        return isValid() == photo.isValid() &&
                Objects.equals(getId(), photo.getId()) &&
                Objects.equals(getName(), photo.getName()) &&
                Objects.equals(getType(), photo.getType()) &&
                Objects.equals(getSize(), photo.getSize()) &&
                Objects.equals(getPath(), photo.getPath()) &&
                Objects.equals(getRoom(), photo.getRoom()) &&
                Objects.equals(getCreatedBy(), photo.getCreatedBy()) &&
                Objects.equals(getCreatedOn(), photo.getCreatedOn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getType(), getSize(), getPath(), getRoom(), getCreatedBy(), getCreatedOn(), isValid());
    }

}
