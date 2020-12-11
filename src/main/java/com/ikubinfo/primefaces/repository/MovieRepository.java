package com.ikubinfo.primefaces.repository;

import java.util.List;

import com.ikubinfo.primefaces.model.Booking;

public interface MovieRepository {

	List<Booking> getMoivesByCategory(int categoryId);

}