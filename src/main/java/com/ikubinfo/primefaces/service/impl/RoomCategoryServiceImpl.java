package com.ikubinfo.primefaces.service.impl;

import com.ikubinfo.primefaces.model.RoomCategory;
import com.ikubinfo.primefaces.repository.RoomCategoryRepository;
import com.ikubinfo.primefaces.service.RoomCategoryService;
import com.ikubinfo.primefaces.service.exceptions.CategoryInUseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("categoryService")
public class RoomCategoryServiceImpl implements RoomCategoryService {

    Logger logger = LoggerFactory.getLogger(RoomCategoryServiceImpl.class);

    private RoomCategoryRepository categoryRepository;

    public RoomCategoryServiceImpl(RoomCategoryRepository categoryRepository) {
        super();
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<RoomCategory> getAll() {
        return categoryRepository.getAllCategories();
    }

    @Override
    public RoomCategory getCategory(int id) {
        return categoryRepository.getRoomCategory(id);
    }

    @Override
    public boolean updateCategory(RoomCategory roomCategory) {
        roomCategory.setUpdatedOn(new Date());
        return categoryRepository.updateCategory(roomCategory);
    }

    @Override
    public boolean create(RoomCategory roomCategory) {
        return categoryRepository.createCategory(roomCategory);
    }

    @Override
    public void delete(RoomCategory roomCategory) throws CategoryInUseException {
        if (categoryRepository.isCategoryInUse(roomCategory)) {
            throw new CategoryInUseException(" Cannot delete this category because it is already in use. ");
        } else {
            categoryRepository.deleteCategory(roomCategory);
        }

    }


    @Override
    public boolean isCategoryInUse(RoomCategory roomCategory) {
        return categoryRepository.isCategoryInUse(roomCategory);
    }
}
