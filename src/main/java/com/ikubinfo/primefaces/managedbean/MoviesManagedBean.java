package com.ikubinfo.primefaces.managedbean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.ikubinfo.primefaces.model.Booking;
import com.ikubinfo.primefaces.service.MovieService;

@ManagedBean
@ViewScoped
public class MoviesManagedBean {

	@ManagedProperty(value = "#{movieService}")
	private MovieService movieService;

	private List<Booking> movies;
	private Booking movie;

	@PostConstruct
	public void init() {

		String value = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		movies = movieService.getMovieById(Integer.parseInt(value));
	}

	public List<Booking> getMovies() {
		return movies;
	}

	public void setMovies(List<Booking> movies) {
		this.movies = movies;
	}

	public MovieService getMovieService() {
		return movieService;
	}

	public void setMovieService(MovieService movieService) {
		this.movieService = movieService;
	}

	public Booking getMovie() {
		return movie;
	}

	public void setMovie(Booking movie) {
		this.movie = movie;
	}

}
