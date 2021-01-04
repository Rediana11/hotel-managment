package com.ikubinfo.primefaces.managedbean;

import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.model.RoomAbility;
import com.ikubinfo.primefaces.model.RoomCategory;
import com.ikubinfo.primefaces.model.RoomFacility;
import com.ikubinfo.primefaces.repository.RoomRepository;
import com.ikubinfo.primefaces.service.RoomService;
import com.ikubinfo.primefaces.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class AddRoomManagedBean {

    private static final Logger LOG = LoggerFactory.getLogger(RoomRepository.class);

    private Room room;
    private double price;
    private int bedsNumber;
    private List<RoomCategory> categories;
    private List<RoomAbility> roomAbilities;
    private List<RoomFacility> roomFacilities;
    private RoomFacility facility ;
    private String[] selectedFacilities;
    private RoomCategory roomCategory;
    private RoomAbility  roomAbility;
    private List<SelectItem> categoryItems;
    private List<SelectItem> abilityItems;

    @ManagedProperty(value = "#{messages}")
    private Messages messages;


    @ManagedProperty(value = "#{roomService}")
    private RoomService roomService;

    @PostConstruct
    public void init() {
        room = new Room();
        categories = roomService.getCategories();
        roomAbilities= roomService.getRoomAbilities();
        roomCategory = new RoomCategory();
        roomAbility = new RoomAbility();
        roomFacilities = roomService.getRoomFacilities();
        facility = new RoomFacility();

    }

    public String save() {
        room.setPrice(price);
        room.setBedsNumber(bedsNumber);
        room.setRoomCategory(roomCategory);
        room.setRoomAbility(roomAbility);
        if(room.getId()==null) {
            if(roomService.create(room)){
                messages.showInfoMessage("Added Successfully!");
            }
            else{
                messages.showErrorMessage("There was a problem adding the room!");
            }
        }
        else{
            roomService.save(room);
            messages.showInfoMessage("Room Updated Successfully!");
        }
        return "room";

    }


    public void loadRoom(){
        if (room.getId()!=null){
        room = roomService.getRoom(room.getId());
        roomCategory=room.getRoomCategory();
        roomAbility=room.getRoomAbility();
        }
        roomCategory=new RoomCategory();
        roomAbility=new RoomAbility();
    }


    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<RoomCategory> getCategories() {
        return categories;
    }

    public RoomService getRoomService() {
        return roomService;
    }

    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    public void setCategories(List<RoomCategory> categories) {
        this.categories = categories;
    }


    public RoomCategory getRoomCategory() {
        return roomCategory;
    }

    public void setRoomCategory(RoomCategory roomCategory) {
        this.roomCategory = roomCategory;
    }

    public RoomAbility getRoomAbility() {
        return roomAbility;
    }

    public void setRoomAbility(RoomAbility roomAbility) {
        this.roomAbility = roomAbility;
    }

    public List<RoomAbility> getRoomAbilities() {
        return roomAbilities;
    }

    public void setRoomAbilities(List<RoomAbility> roomAbilities) {
        this.roomAbilities = roomAbilities;
    }

    public List<SelectItem> getCategoryItems() {
        return categoryItems;
    }

    public void setCategoryItems(List<SelectItem> categoryItems) {
        this.categoryItems = categoryItems;
    }

    public List<SelectItem> getAbilityItems() {
        return abilityItems;
    }

    public void setAbilityItems(List<SelectItem> abilityItems) {
        this.abilityItems = abilityItems;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getBedsNumber() {
        return bedsNumber;
    }

    public void setBedsNumber(int bedsNumber) {
        this.bedsNumber = bedsNumber;
    }

    public Messages getMessages() {
        return messages;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }

    public List<RoomFacility> getRoomFacilities() {
        return roomFacilities;
    }

    public void setRoomFacilities(List<RoomFacility> roomFacilities) {
        this.roomFacilities = roomFacilities;
    }

    public String[] getSelectedFacilities() {
        return selectedFacilities;
    }

    public void setSelectedFacilities(String[] selectedFacilities) {
        this.selectedFacilities = selectedFacilities;
    }

    public RoomFacility getFacility() {
        return facility;
    }

    public void setFacility(RoomFacility facility) {
        this.facility = facility;
    }
}