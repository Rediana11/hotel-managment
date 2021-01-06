package com.ikubinfo.primefaces.repository;

import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.model.RoomCategory;

import java.util.List;

public interface RoomCategoryRepository {

    List<RoomCategory> getAllCategories();

    RoomCategory getRoomCategory(int id);

    boolean updateCategory(RoomCategory roomCategory);

    boolean createCategory(RoomCategory roomCategory);

    //boolean isCategoryInUse(Room room);

    void deleteCategory(RoomCategory roomCategory);
}
