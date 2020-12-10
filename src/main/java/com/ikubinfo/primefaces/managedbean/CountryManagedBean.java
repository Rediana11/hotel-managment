package com.ikubinfo.primefaces.managedbean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.ikubinfo.primefaces.model.Country;
import com.ikubinfo.primefaces.service.CountryService;

@ManagedBean
@ViewScoped
public class CountryManagedBean implements Serializable {

	private static final long serialVersionUID = 5448895347701230045L;
	private Country selectedCountry;
	private List<Country> countries;
	private String continent;
	private BigDecimal surface;

	@ManagedProperty(value = "#{countryService}")
	private CountryService countryService;

	@PostConstruct
	public void init() {

		countries = countryService.getAll(continent, surface);
		selectedCountry = new Country();

	}

	public void save() {
		countryService.save(selectedCountry);
		countries = countryService.getAll(continent, surface);
		selectedCountry = new Country();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Updated"));
	}

	public void getAll() {
		countries = countryService.getAll(continent, surface);

	}

	public List<Country> getCountries() {
		return countries;
	}

	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

	public BigDecimal getSurface() {
		return surface;
	}

	public void setSurface(BigDecimal surface) {
		this.surface = surface;
	}

	public CountryService getCountryService() {
		return countryService;
	}

	public void setCountryService(CountryService countryService) {
		this.countryService = countryService;
	}

	public Country getSelectedCountry() {
		return selectedCountry;
	}

	public void setSelectedCountry(Country selectedCountry) {
		this.selectedCountry = selectedCountry;
	}

}
