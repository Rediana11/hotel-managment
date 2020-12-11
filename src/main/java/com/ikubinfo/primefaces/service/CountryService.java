package com.ikubinfo.primefaces.service;

import java.math.BigDecimal;
import java.util.List;

import com.ikubinfo.primefaces.model.User;

public interface CountryService {

	List<User> getAll(String continent, BigDecimal surface);

	boolean save(User country);

}