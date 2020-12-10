package com.ikubinfo.primefaces.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ikubinfo.primefaces.model.Country;
import com.ikubinfo.primefaces.repository.CountryRepository;
import com.ikubinfo.primefaces.service.CountryService;

@Service("countryService")
class CountryServiceImpl implements CountryService {

	private CountryRepository countryRepository;

	public CountryServiceImpl(CountryRepository countryRepository) {
		super();
		this.countryRepository = countryRepository;
	}

	@Override
	public List<Country> getAll(String continent, BigDecimal surface) {

		return countryRepository.getAll(continent, surface);

	}

	@Override
	public boolean save(Country country) {
		return countryRepository.save(country);

	}
}
