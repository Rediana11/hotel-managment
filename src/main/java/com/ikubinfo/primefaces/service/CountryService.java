package com.ikubinfo.primefaces.service;

import java.math.BigDecimal;
import java.util.List;

import com.ikubinfo.primefaces.model.Country;

public interface CountryService {

	List<Country> getAll(String continent, BigDecimal surface);

	boolean save(Country country);

}