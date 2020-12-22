package com.ikubinfo.primefaces.managedbean;

import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.model.RoomAbility;
import com.ikubinfo.primefaces.model.RoomCategory;
import com.ikubinfo.primefaces.repository.RoomRepository;
import com.ikubinfo.primefaces.service.RoomService;
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
    private List<RoomCategory> categories;
    private List<RoomAbility> roomAbilities;

    private RoomCategory roomCategory;
    private RoomAbility  roomAbility;
    private List<SelectItem> categoryItems;
    private List<SelectItem> abilityItems;


    @ManagedProperty(value = "#{roomService}")
    private RoomService roomService;

    @PostConstruct
    public void init() {
        room = new Room();
        categoryItems = new ArrayList<SelectItem>();
        abilityItems = new ArrayList<SelectItem>();
        categories = roomService.getCategories();
        for(RoomCategory category: categories){
            categoryItems.add(new SelectItem(category.getId(), category.getName()));
        }
        roomAbilities= roomService.getRoomAbilities();
        for (RoomAbility ability: roomAbilities){
            abilityItems.add(new SelectItem(ability.getId(),ability.getName()));
        }
    }

    public String save() {

        if(room.getId()==null) {
            roomService.create(room);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful Added"));
        }
        else{
            roomService.save(room);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful Updated!"));
        }
        return "/room?faces-redirect=true";

    }
    public List<SelectItem> getCategories(){
        List<SelectItem> items = new ArrayList<SelectItem>();
         categories = roomService.getCategories();

        for(RoomCategory category: categories){
            items.add(new SelectItem(category.getId(), category.getName()));
        }
        return items;
    }

    public void loadRoom(){
        if (room.getId()!=null)
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
}