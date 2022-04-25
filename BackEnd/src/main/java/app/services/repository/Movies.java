package app.services.repository;

import app.db.MovieDB;
import app.model.Movie;
import app.services.modelJSON.MovieJSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Movies {
    private static Movies instance;
    public static List<MovieJSON> moviesList;

    private Movies() throws IOException {
        moviesList = new ArrayList<>();
        List<Movie> movies= MovieDB.getInstance().getMovies();
        for (Movie movie : movies) {
            moviesList.add(new MovieJSON(movie.getId()));
        }
    }
    public static Movies getInstance() throws IOException {
        if (instance == null)
            instance = new Movies();
        return instance;
    }

    public List<MovieJSON> getMoviesList() {
        return moviesList;
    }

    public List<MovieJSON> getMoviesList(List<Movie> movies) throws IOException {
        List<MovieJSON> moviesJSON = new ArrayList<>();
        for (Movie movie : movies) {
            moviesJSON.add(new MovieJSON(movie.getId()));
        }
        return moviesJSON;
    }
}
