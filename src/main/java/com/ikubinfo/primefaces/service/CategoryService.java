package com.ikubinfo.primefaces.service;

import java.util.List;

import com.ikubinfo.primefaces.model.Role;
import com.ikubinfo.primefaces.service.exceptions.CategoryInUseException;

public interface CategoryService {

	List<Role> getAll(String name);

	boolean save(Role category);

	boolean create(Role category);

	void delete(Role category) throws CategoryInUseException;

}