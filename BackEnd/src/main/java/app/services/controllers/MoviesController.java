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

    @PostMapping(path = "/search", consumes = "application/json", produces = "application/json")
    public List<MovieJSON> search(@RequestBody(required = true) String jsonString, final HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        String searchKey = (gson.fromJson(jsonString, Properties.class)).getProperty("search");
        MovieDB.getInstance().FilterMovies(searchKey);
        return getMovies();
    }

}
