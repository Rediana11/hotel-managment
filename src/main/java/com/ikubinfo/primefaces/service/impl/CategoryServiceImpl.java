package com.ikubinfo.primefaces.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ikubinfo.primefaces.model.Category;
import com.ikubinfo.primefaces.repository.CategoryRepository;
import com.ikubinfo.primefaces.service.CategoryService;
import com.ikubinfo.primefaces.service.exceptions.CategoryInUseException;

@Service("categoryService")
 class CategoryServiceImpl implements CategoryService {

	private CategoryRepository categoryRepository;

	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public List<Category> getAll(String name) {
		return categoryRepository.getAll(name);
	}

	@Override
	public boolean save(Category category) {
		category.setLastUpdated(new Date());
		return categoryRepository.save(category);

	}

	@Override
	public boolean create(Category category) {
		category.setLastUpdated(new Date());
		return categoryRepository.create(category);

	}

	@Override
	public void delete(Category category) throws CategoryInUseException {
		if (categoryRepository.isCategoryInUse(category)) {
			throw new CategoryInUseException("Cannot delete this category because it is already in use.");
		} else {
			categoryRepository.delete(category);
		}

	}

}
