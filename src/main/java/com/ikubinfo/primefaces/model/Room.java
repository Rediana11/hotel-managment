package com.ikubinfo.primefaces.model;


import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Room {

    private Integer id;
    private String name;
    private String description;
    private Integer bedsNumber;
    private Double price;
    private String facilities;
    private RoomAbility roomAbility;
    private RoomCategory roomCategory;
    private List<Booking> bookings;
    private List<RoomFacility> roomFacilities;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getBedsNumber() {
        return bedsNumber;
    }

    public void setBedsNumber(Integer bedsNumber) {
        this.bedsNumber = bedsNumber;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public RoomAbility getRoomAbility() {
        return roomAbility;
    }

    public void setRoomAbility(RoomAbility roomAbility) {
        this.roomAbility = roomAbility;
    }

    public RoomCategory getRoomCategory() {
        return roomCategory;
    }

    public void setRoomCategory(RoomCategory roomCategory) {
        this.roomCategory = roomCategory;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
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


    public List<RoomFacility> getRoomFacilities() {
        return roomFacilities;
    }

    public void setRoomFacilities(List<RoomFacility> roomFacilities) {
        this.roomFacilities = roomFacilities;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", bedsNumber=" + bedsNumber +
                ", price=" + price +
                ", roomAbility=" + roomAbility +
                ", roomCategory=" + roomCategory +
                ", bookings=" + bookings +
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
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return isValid() == room.isValid() &&
                Objects.equals(getId(), room.getId()) &&
                Objects.equals(getName(), room.getName()) &&
                Objects.equals(getDescription(), room.getDescription()) &&
                Objects.equals(getBedsNumber(), room.getBedsNumber()) &&
                Objects.equals(getPrice(), room.getPrice()) &&
                Objects.equals(getFacilities(), room.getFacilities()) &&
                Objects.equals(getRoomAbility(), room.getRoomAbility()) &&
                Objects.equals(getRoomCategory(), room.getRoomCategory()) &&
                Objects.equals(getBookings(), room.getBookings()) &&
                Objects.equals(getCreatedBy(), room.getCreatedBy()) &&
                Objects.equals(getCreatedOn(), room.getCreatedOn()) &&
                Objects.equals(getUpdatedBy(), room.getUpdatedBy()) &&
                Objects.equals(getUpdatedOn(), room.getUpdatedOn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getBedsNumber(), getPrice(), getFacilities(), getRoomAbility(), getRoomCategory(), getBookings(), getCreatedBy(), getCreatedOn(), getUpdatedBy(), getUpdatedOn(), isValid());
    }
}
