package com.ikubinfo.primefaces.repository;

import java.util.List;

import com.ikubinfo.primefaces.model.Category;

public interface CategoryRepository {

	List<Category> getAll(String name);

	boolean save(Category category);

	boolean create(Category category);

	boolean isCategoryInUse(Category category);

	void delete(Category category);

}