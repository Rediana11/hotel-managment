package com.ikubinfo.primefaces.model;

import java.util.Date;
import java.util.Objects;

public class RoomPhoto {

    private Integer id;
    private String name;
    private String type;
    private String size;
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

    @Override
    public String toString() {
        return "RoomPhoto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", size='" + size + '\'' +
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
        RoomPhoto roomPhoto = (RoomPhoto) o;
        return isValid() == roomPhoto.isValid() &&
                Objects.equals(getId(), roomPhoto.getId()) &&
                Objects.equals(getName(), roomPhoto.getName()) &&
                Objects.equals(getType(), roomPhoto.getType()) &&
                Objects.equals(getSize(), roomPhoto.getSize()) &&
                Objects.equals(getRoom(), roomPhoto.getRoom()) &&
                Objects.equals(getCreatedBy(), roomPhoto.getCreatedBy()) &&
                Objects.equals(getCreatedOn(), roomPhoto.getCreatedOn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getType(), getSize(), getRoom(), getCreatedBy(), getCreatedOn(), isValid());
    }
}
