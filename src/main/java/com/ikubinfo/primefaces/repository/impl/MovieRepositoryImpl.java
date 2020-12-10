package com.ikubinfo.primefaces.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ikubinfo.primefaces.exception.ApfasException;
import com.ikubinfo.primefaces.model.Movie;
import com.ikubinfo.primefaces.repository.MovieRepository;

@Repository
class MovieRepositoryImpl implements MovieRepository {
	Logger logger = LoggerFactory.getLogger(MovieRepositoryImpl.class);

	private static String GET_MOVIES_BY_CATEGORY = "Select film.title,film.description,film.rental_rate,film.last_update,film.release_year,public.language.name\r\n"
			+ "from film \r\n" + "inner join film_category on film_category.film_id = film.film_id\r\n"
			+ "inner join public.language on public.language.language_id = film.language_id\r\n"
			+ "where film_category.category_id = ? \r\n" + "order by film.release_year DESC";

	private DataSource dataSource;

	public MovieRepositoryImpl(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	@Override
	public List<Movie> getMoivesByCategory(int categoryId) {

		List<Movie> toReturn = new ArrayList<Movie>();

		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(GET_MOVIES_BY_CATEGORY);) {

			statement.setInt(1, categoryId);

			ResultSet result = statement.executeQuery();

			while (result.next()) {

				Movie movie = new Movie();

				movie.setTitle(result.getString("title"));
				movie.setDescription(result.getString("description"));
				movie.setRentalRate(result.getBigDecimal("rental_rate"));
				movie.setLastUpdate(new Date(result.getTimestamp("last_update").getTime()));
				movie.setReleaseYear(result.getInt("release_year"));
				movie.setLanguage(result.getString("name"));

				toReturn.add(movie);

			}

			return toReturn;

		} catch (SQLException e) {
			logger.error("An error occured during query execution");
			throw new ApfasException(e.getMessage());
		}

	}

}
