package com.ikubinfo.primefaces.managedbean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.ikubinfo.primefaces.model.Movie;
import com.ikubinfo.primefaces.service.MovieService;

@ManagedBean
@ViewScoped
public class MoviesManagedBean {

	@ManagedProperty(value = "#{movieService}")
	private MovieService movieService;

	private List<Movie> movies;
	private Movie movie;

	@PostConstruct
	public void init() {

		String value = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		movies = movieService.getMovieById(Integer.parseInt(value));
	}

	public List<Movie> getMovies() {
		return movies;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}

	public MovieService getMovieService() {
		return movieService;
	}

	public void setMovieService(MovieService movieService) {
		this.movieService = movieService;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

}
