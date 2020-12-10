package com.ikubinfo.primefaces.repository;

import java.math.BigDecimal;
import java.util.List;

import com.ikubinfo.primefaces.model.Country;

public interface CountryRepository {

	List<Country> getAll(String continent, BigDecimal surface);

	boolean save(Country country);

}