package ir.ac.ut.iemdb.controller;

import ir.ac.ut.iemdb.model.Movie;
import ir.ac.ut.iemdb.repository.MovieRepository;
import org.springframework.web.bind.annotation.*;

import java.sql.Array;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {

    private final MovieRepository repository = MovieRepository.getInstance();

    //    id, name, summary, releaseDate, director, writers, genres, cast, imdbRate, duration, ageLimit, image, coverImage
    @PostMapping("/insert/{id}/{name}/{summary}/{releaseDate}/{director}/{writers}/{genres}/{cast}/{imdbRate}/{duration}/{ageLimit}/{image}/{coverImage}" )
    public void insertMovie(@PathVariable Long id, @PathVariable String name, @PathVariable String summary,
                            @PathVariable Date releaseDate, @PathVariable String director, @PathVariable String writers,
                            @PathVariable Double imdbRate, @PathVariable Integer duration, @PathVariable Integer ageLimit,
                            @PathVariable Double rating, @PathVariable Integer ratingCount, @PathVariable String image,
                            @PathVariable String coverImage
    ) throws SQLException {
        repository.insert(new Movie(id, name, summary, releaseDate, director, writers, imdbRate, duration, ageLimit, image, coverImage));
    }

    @GetMapping("/")
    public List<Movie> findAll() throws SQLException {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Movie findById(@PathVariable String id) throws SQLException {
        return repository.findById(id);
    }


}
