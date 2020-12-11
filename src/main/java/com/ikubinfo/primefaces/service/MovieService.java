package com.ikubinfo.primefaces.service;

import java.util.List;

import com.ikubinfo.primefaces.model.Booking;

public interface MovieService {

	List<Booking> getMovieById(int categoryId);

}