package com.ikubinfo.primefaces.managedbean;

import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.model.RoomCategory;
import com.ikubinfo.primefaces.service.RoomCategoryService;
import com.ikubinfo.primefaces.service.RoomService;
import com.ikubinfo.primefaces.service.exceptions.CategoryInUseException;
import com.ikubinfo.primefaces.service.impl.RoomCategoryServiceImpl;
import com.ikubinfo.primefaces.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@ViewScoped
public class CategoryManagedBean implements Serializable {


    Logger logger = LoggerFactory.getLogger(RoomCategoryServiceImpl.class);

    private RoomCategory roomCategory;

    private List<RoomCategory> categories;
    private String name;

    @ManagedProperty(value = "#{categoryService}")
    private RoomCategoryService categoryService;

    @ManagedProperty(value = "#{messages}")
    private Messages messages;

    @PostConstruct
    public void init()
    {

        logger.info(categoryService.getAll().toString());
        categories = categoryService.getAll();
        roomCategory = new RoomCategory();

    }

    public void save() {
        if (categoryService.save(roomCategory)) {
            getAll();
            messages.showInfoMessage("Room updated successfully");
        }
        roomCategory = new RoomCategory();

    }

    public void add() {

            categoryService.create(roomCategory);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful Added"));

    }
    public void filter() {
        categories = categoryService.getAll();
    }

    public void delete() {
            categoryService.delete(roomCategory);
            categories = categoryService.getAll();
            messages.showInfoMessage("Deleted");


    }

    public void getAll() {
        categories = categoryService.getAll();
    }


    public RoomCategory getRoomCategory() {
        return roomCategory;
    }

    public void setRoomCategory(RoomCategory roomCategory) {
        this.roomCategory = roomCategory;
    }

    public List<RoomCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<RoomCategory> categories) {
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoomCategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(RoomCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public Messages getMessages() {
        return messages;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }
}
