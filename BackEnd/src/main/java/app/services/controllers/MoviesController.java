package app.services.controllers;

import app.db.MovieDB;
import app.services.modelJSON.MovieJSON;
import app.services.repository.Movies;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping("/movies")
public class MoviesController {

    @GetMapping("")
    public List<MovieJSON> getMovies() throws IOException {
        return Movies.getInstance().getMoviesList();
    }

    @GetMapping("/sort_by_imdb")
    public List<MovieJSON> getMoviesImdbSort() throws IOException {
        MovieDB.getInstance().SortByImdbRate();
        return getMovies();
    }

    @GetMapping("/sort_by_date")
    public List<MovieJSON> getMoviesDateSort() throws IOException {
        MovieDB.getInstance().SortByDate();
        return getMovies();
    }

    @GetMapping("/clear")
    public List<MovieJSON> getMoviesClearFilter() throws IOException {
        MovieDB.getInstance().clearSearchFilter();
        return getMovies();
    }

    @GetMapping("/search/{searchKey}")
    public List<MovieJSON> search(@PathVariable String searchKey, final HttpServletResponse response) throws IOException {
        MovieDB.getInstance().FilterMovies(searchKey);
        return getMovies();
    }

}
