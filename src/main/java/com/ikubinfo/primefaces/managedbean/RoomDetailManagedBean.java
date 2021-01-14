package com.ikubinfo.primefaces.managedbean;

import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.model.RoomCategory;
import com.ikubinfo.primefaces.model.RoomPhoto;
import com.ikubinfo.primefaces.service.PhotoService;
import com.ikubinfo.primefaces.service.RoomService;
import com.ikubinfo.primefaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@ViewScoped
public class RoomDetailManagedBean implements Serializable {

    private Room room;
    private RoomCategory category;
    private List<RoomPhoto> photos;
    private RoomPhoto photo;


    @ManagedProperty(value = "#{roomService}")
    private RoomService roomService;

    @ManagedProperty(value = "#{photoService}")
    private PhotoService photoService;

    @ManagedProperty(value = "#{messages}")
    private Messages messages;

    @PostConstruct
    public void init() {
        room= new Room();
        photo = new RoomPhoto();
        category = new RoomCategory();
    }

    public void loadRoom(){
        if(room!=null){
            room = roomService.getRoom(room.getId());
            photos = photoService.getAll(room.getId());

        }
        else{
            messages.showErrorMessage("Room with this id does not exist");
        }

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

    public RoomCategory getCategory() {
        return category;
    }

    public void setCategory(RoomCategory category) {
        this.category = category;
    }

    public Messages getMessages() {
        return messages;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }

    public List<RoomPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<RoomPhoto> photos) {
        this.photos = photos;
    }

    public RoomPhoto getPhoto() {
        return photo;
    }

    public void setPhoto(RoomPhoto photo) {
        this.photo = photo;
    }

    public PhotoService getPhotoService() {
        return photoService;
    }

    public void setPhotoService(PhotoService photoService) {
        this.photoService = photoService;
    }

}
