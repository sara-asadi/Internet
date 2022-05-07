package ir.ac.ut.iemdb.controller;

import ir.ac.ut.iemdb.model.Film;
import ir.ac.ut.iemdb.model.IEMDB;
import ir.ac.ut.iemdb.repository.FilmRepository;
import ir.ac.ut.iemdb.repository.IEMDBRepository;
import org.springframework.web.bind.annotation.*;

import java.sql.Array;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("iemdb")
public class FilmController {

    private final FilmRepository repository = FilmRepository.getInstance();

    //    id, name, summary, releaseDate, director, writers, genres, cast, imdbRate, duration, ageLimit, image, coverImage
    @PostMapping("/insert/{id}/{name}/{summary}/{releaseDate}/{director}/{writers}/{genres}/{cast}/{imdbRate}/{duration}/{ageLimit}/{image}/{coverImage}")
    public void insertFilm(@PathVariable String id, @PathVariable String name, @PathVariable String summary,
                           @PathVariable Date releaseDate, @PathVariable String director, @PathVariable Array writers,
                           @PathVariable Array genres, @PathVariable Array cast, @PathVariable Double imdbRate,
                           @PathVariable Double duration, @PathVariable Integer ageLimit, @PathVariable String image, @PathVariable String coverImage
    ) throws SQLException {
        repository.insert(new Film(id, name, summary, releaseDate, director, writers, genres, cast, imdbRate, duration, ageLimit, image, coverImage));
    }

    @GetMapping("/find/{id}")
    public Film findById(@PathVariable String id) throws SQLException {
        return repository.findById(id);
    }

    @GetMapping("/find")
    public List<Film> findAll() throws SQLException {
        return repository.findAll();
    }
}
