package com.ikubinfo.primefaces.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ikubinfo.primefaces.model.Movie;
import com.ikubinfo.primefaces.repository.MovieRepository;
import com.ikubinfo.primefaces.service.MovieService;

@Service("movieService")
class MovieServiceImpl implements MovieService {

	private MovieRepository movieRepository;

	public MovieServiceImpl(MovieRepository movieRepository) {
		super();
		this.movieRepository = movieRepository;
	}

	@Override
	public List<Movie> getMovieById(int categoryId) {

		return movieRepository.getMoivesByCategory(categoryId);

	}
}
