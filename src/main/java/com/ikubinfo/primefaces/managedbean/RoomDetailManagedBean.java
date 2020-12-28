package com.ikubinfo.primefaces.managedbean;

import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.service.RoomService;
import com.ikubinfo.primefaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@ViewScoped
public class RoomDetailManagedBean implements Serializable {

    private Room room;

    @ManagedProperty(value = "#{roomService}")
    private RoomService roomService;

    @ManagedProperty(value = "#{messages}")
    private Messages messages;

    @PostConstruct
    public void init() {
        room= new Room();
    }

    public void loadRoom(){
        room = roomService.getRoom(room.getId());

    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public RoomService getRoomService() {
        return roomService;
    }

    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    public Messages getMessages() {
        return messages;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }
}
