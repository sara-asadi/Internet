package ir.ac.ut.iemdb.controller;

import ir.ac.ut.iemdb.model.Movie;
import ir.ac.ut.iemdb.model.WatchList;
import ir.ac.ut.iemdb.repository.CastRepository;
import ir.ac.ut.iemdb.repository.UserRepository;
import ir.ac.ut.iemdb.repository.WatchListRepository;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("watchlist")
public class WatchListController {
    private final WatchListRepository repository = WatchListRepository.getInstance();

    @GetMapping("")
    public List<Movie> getWatchlist() throws SQLException {
        return repository.getUserWatchListMovies(UserRepository.getCurrentUser());
    }

    @GetMapping("remove/{movieId}")
    public String getWatchlist(@PathVariable Integer movieId) throws SQLException {
        repository.remove(movieId, UserRepository.getCurrentUser());
        return "movie removed from your watchlist";
    }
}
