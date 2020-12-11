package com.ikubinfo.primefaces.repository;

import java.util.List;

import com.ikubinfo.primefaces.model.Role;

public interface CategoryRepository {

	List<Role> getAll(String name);

	boolean save(Role category);

	boolean create(Role category);

	boolean isCategoryInUse(Role category);

	void delete(Role category);

}