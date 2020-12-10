package com.ikubinfo.primefaces.repository;

import java.util.List;

import com.ikubinfo.primefaces.model.Movie;

public interface MovieRepository {

	List<Movie> getMoivesByCategory(int categoryId);

}