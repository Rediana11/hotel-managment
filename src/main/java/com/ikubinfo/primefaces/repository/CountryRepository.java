package com.ikubinfo.primefaces.repository;

import java.math.BigDecimal;
import java.util.List;

import com.ikubinfo.primefaces.model.User;

public interface CountryRepository {

	List<User> getAll(String continent, BigDecimal surface);

	boolean save(User country);

}