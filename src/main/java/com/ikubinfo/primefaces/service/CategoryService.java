package com.ikubinfo.primefaces.service;

import java.util.List;

import com.ikubinfo.primefaces.model.Category;
import com.ikubinfo.primefaces.service.exceptions.CategoryInUseException;

public interface CategoryService {

	List<Category> getAll(String name);

	boolean save(Category category);

	boolean create(Category category);

	void delete(Category category) throws CategoryInUseException;

}