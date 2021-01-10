package com.ikubinfo.primefaces.managedbean;

import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.model.RoomCategory;
import com.ikubinfo.primefaces.service.RoomCategoryService;
import com.ikubinfo.primefaces.service.RoomService;
import com.ikubinfo.primefaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class CategoryDetailManagedBean implements Serializable {

    private RoomCategory roomCategory;

    @ManagedProperty(value = "#{categoryService}")
    private RoomCategoryService categoryService;

    @ManagedProperty(value = "#{messages}")
    private Messages messages;

    @PostConstruct
    public void init() {
        roomCategory= new RoomCategory();
    }

    public void loadCategory(){
        roomCategory = categoryService.getCategory(roomCategory.getId());
        if (roomCategory==null){
            messages.showErrorMessage("Category does not exist");
        }
    }



    public Messages getMessages() {
        return messages;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }

    public RoomCategory getRoomCategory() {
        return roomCategory;
    }

    public void setRoomCategory(RoomCategory roomCategory) {
        this.roomCategory = roomCategory;
    }

    public RoomCategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(RoomCategoryService categoryService) {
        this.categoryService = categoryService;
    }
}
