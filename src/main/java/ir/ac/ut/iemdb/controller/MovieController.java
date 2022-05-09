package ir.ac.ut.iemdb.controller;

import ir.ac.ut.iemdb.model.*;
import ir.ac.ut.iemdb.repository.*;
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

    @GetMapping( "/comment/{id}" )
    public List<Comment> getComments(@PathVariable Integer id) throws SQLException {
        return CommentRepository.getInstance().findByMovieId(id);
    }

    @GetMapping( "/comment/{text}/{movieId}" )
    public String comment(@PathVariable String text,@PathVariable Integer movieId) throws SQLException {
        CommentRepository.getInstance().insert(new Comment("saman@ut.ac.ir", movieId, text));
        return "comment added";
    }

    @GetMapping( "/comment/vote/{commentId}/{vote}" )
    public Comment vote(@PathVariable String vote, @PathVariable Integer commentId) throws SQLException {
        switch (vote) {
            case "like": {VotesRepository.getInstance().insert(new Vote(commentId, "saman@ut.ac.ir", 1));
                break;}
            case "dislike": {VotesRepository.getInstance().insert(new Vote(commentId, "saman@ut.ac.ir", -1));
                break;}
        }
        return CommentRepository.getInstance().findById(String.valueOf(commentId));
    }
}