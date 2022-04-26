package app.services.controllers;

import app.db.UserDB;
import app.model.Movie;
import app.model.User;
import app.services.modelJSON.MovieJSON;
import app.services.repository.Movies;
import app.tools.Response;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping("/watchlist")
public class WatchListController {
    @GetMapping("")
    public List<MovieJSON> getWatchlist() throws IOException {
        User user = UserDB.getInstance().getCurrentUser();
        return Movies.getInstance().getMoviesList(user.getWatchListMovies());
    }

    @GetMapping("/recommendations")
    public List<MovieJSON> getRecommendations() throws IOException {
        User user = UserDB.getInstance().getCurrentUser();
        return Movies.getInstance().getMoviesList(user.getRecommendationList());
    }

    @GetMapping("/remove/{movieId}")
    public String removeFromWatchlist(@PathVariable long movieId, final HttpServletResponse response) throws IOException {
        User user = UserDB.getInstance().getCurrentUser();
        if (user.isInWatchList(movieId)) {
            user.removeWatchList(movieId);
        } else {
            response.sendError(HttpStatus.BAD_REQUEST.value(), "couldn't remove!");
            return null;
        }
        return Response.OK_RESPONSE;
    }
}
