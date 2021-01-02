package com.ikubinfo.primefaces.service;

import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.model.RoomAbility;
import com.ikubinfo.primefaces.model.RoomCategory;
import com.ikubinfo.primefaces.service.exceptions.CategoryInUseException;

import java.util.List;

public interface RoomCategoryService {

    List<RoomCategory> getAll();

    RoomCategory getCategory(int id);

    boolean updateCategory(RoomCategory roomCategory);

    boolean create(RoomCategory roomCategory);

    void delete(RoomCategory roomCategory) ;

}
