package com.ikubinfo.primefaces.model;

import java.util.List;

public class RoomFacility {

    private Integer id;
    private String name;
    private String description;

    public RoomFacility(Integer roomFacilityId) {
        this.id=roomFacilityId;
    }

    public RoomFacility() {

    }
    //private List<Room> rooms;

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

/*    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }*/

    @Override
    public String toString() {
        return "RoomFacility{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
