package com.ikubinfo.primefaces.service;

import java.util.List;

import com.ikubinfo.primefaces.model.Movie;

public interface MovieService {

	List<Movie> getMovieById(int categoryId);

}