package ir.ac.ut.iemdb.controller;

import ir.ac.ut.iemdb.model.Actor;
import ir.ac.ut.iemdb.model.Comment;
import ir.ac.ut.iemdb.model.Movie;
import ir.ac.ut.iemdb.model.Rate;
import ir.ac.ut.iemdb.repository.CastRepository;
import ir.ac.ut.iemdb.repository.CommentRepository;
import ir.ac.ut.iemdb.repository.MovieRepository;
import ir.ac.ut.iemdb.repository.RatesRepository;
import org.springframework.web.bind.annotation.*;

import java.sql.Array;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {

    private final MovieRepository repository = MovieRepository.getInstance();

    @PostMapping( "/insert/{id}/{name}/{summary}/{releaseDate}/{director}/{writers}/{genres}/{cast}/{imdbRate}/{duration}/{ageLimit}/{image}/{coverImage}" )
    public void insertMovie(@PathVariable Integer id, @PathVariable String name, @PathVariable String summary,
                            @PathVariable String releaseDate, @PathVariable String director, @PathVariable String writers,
                            @PathVariable Double imdbRate, @PathVariable Integer duration, @PathVariable Integer ageLimit,
                            @PathVariable Double rating, @PathVariable Integer ratingCount, @PathVariable String image,
                            @PathVariable String coverImage
    ) throws SQLException {
        repository.insert(new Movie(id, name, summary, releaseDate, director, writers, imdbRate, duration, ageLimit, image, coverImage));
    }

    @GetMapping( "" )
    public List<Movie> findAll() throws SQLException {
        return repository.findAll();
    }

    @GetMapping( "/{id}" )
    public Movie findById(@PathVariable String id) throws SQLException {
        return repository.findById(id);
    }

    @GetMapping( "/cast/{id}" )
    public List<Actor> getCast(@PathVariable Integer id) throws SQLException {
        return CastRepository.getInstance().getCast(id);
    }

    @GetMapping( "/comments/{id}" )
    public List<Comment> getComments(@PathVariable Integer id) throws SQLException {
        return CommentRepository.getInstance().findByMovieId(id);
    }

    @GetMapping( "/sort/{sortBy}" )
    public List<Movie> sortByRate(@PathVariable String sortBy) throws SQLException {
        switch (sortBy) {
            case "rate": return MovieRepository.getInstance().sort("imdbRate");
            case "date": return MovieRepository.getInstance().sort("releaseDate");
            default: return MovieRepository.getInstance().sort("imdbRate");
        }
    }

    @GetMapping( "/clear" )
    public List<Movie> clear() throws SQLException {
        return MovieRepository.getInstance().sort("imdbRate");
    }

    @GetMapping( "/search/{key}" )
    public List<Movie> search(@PathVariable String key) throws SQLException {
        return MovieRepository.getInstance().search(key);
    }

    @GetMapping( "/rate/{rate}/{movieId}" )
    public Movie rate(@PathVariable Integer rate,@PathVariable Integer movieId) throws SQLException {
        RatesRepository.getInstance().insert(new Rate(movieId, "saman@ut.ac.ir", rate));
        return MovieRepository.getInstance().findById(String.valueOf(movieId));
    }

}