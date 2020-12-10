package com.ikubinfo.primefaces.model;

import java.math.BigDecimal;

public class Country {

	private String countryCode;
	private String name;
	private String continent;
	private BigDecimal surfaceArea;
	private int indepencenceYear;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

	public BigDecimal getSurfaceArea() {
		return surfaceArea;
	}

	public void setSurfaceArea(BigDecimal surfaceArea) {
		this.surfaceArea = surfaceArea;
	}

	public int getIndepencenceYear() {
		return indepencenceYear;
	}

	public void setIndepencenceYear(int indepencenceYear) {
		this.indepencenceYear = indepencenceYear;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((countryCode == null) ? 0 : countryCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (countryCode == null) {
			if (other.countryCode != null)
				return false;
		} else if (!countryCode.equals(other.countryCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Country [countryCode=" + countryCode + ", name=" + name + ", continent=" + continent + ", surfaceArea="
				+ surfaceArea + ", indepencenceYear=" + indepencenceYear + "]";
	}

}
