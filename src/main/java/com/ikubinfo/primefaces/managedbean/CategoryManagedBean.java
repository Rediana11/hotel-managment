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
import java.util.Date;
import java.util.List;

@ManagedBean
@ViewScoped
public class CategoryManagedBean implements Serializable {


    Logger logger = LoggerFactory.getLogger(RoomCategoryServiceImpl.class);

    private RoomCategory roomCategory;

    private List<RoomCategory> categories;

    @ManagedProperty(value = "#{categoryService}")
    private RoomCategoryService categoryService;

    @ManagedProperty(value = "#{messages}")
    private Messages messages;

    @ManagedProperty(value = "#{loggedUserMangedBean}")
    private LoggedUserMangedBean loggedUserMangedBean;

    @PostConstruct
    public void init() {
        categories = categoryService.getAll();
        roomCategory = new RoomCategory();
    }

    public void updateCategory() throws CategoryInUseException {
        roomCategory.setUpdatedBy(loggedUserMangedBean.getUser());
        try {
            if (categoryService.updateCategory(roomCategory)) {
                getAll();
                messages.showInfoMessage("Category updated successfully");

            } else {
                messages.showErrorMessage("There was a problem updating the category");
            }
        }
        catch (CategoryInUseException e) {
            messages.showWarningMessage(e.getMessage());
        }
        roomCategory = new RoomCategory();
    }

    public void delete() {
        try {
            categoryService.delete(roomCategory);
            messages.showInfoMessage(" Category deleted! ");
            categories = categoryService.getAll();
        } catch (CategoryInUseException e) {
            messages.showWarningMessage(e.getMessage());
        }
    }


    public void add() {
        roomCategory.setCreatedBy(loggedUserMangedBean.getUser());
        if (categoryService.create(roomCategory)) {
            messages.showInfoMessage("Category was added successfully");
            getAll();
        } else {
            messages.showErrorMessage("There was a problem adding the category");
        }
        roomCategory = new RoomCategory();

    }

    public void filter() {
        categories = categoryService.getAll();
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

    public RoomCategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(RoomCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public Messages getMessages() {
        return messages;
    }

    public LoggedUserMangedBean getLoggedUserMangedBean() {
        return loggedUserMangedBean;
    }

    public void setLoggedUserMangedBean(LoggedUserMangedBean loggedUserMangedBean) {
        this.loggedUserMangedBean = loggedUserMangedBean;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }
}
