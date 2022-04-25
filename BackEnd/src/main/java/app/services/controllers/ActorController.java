package app.services.controllers;

import app.db.ActorDB;
import app.model.Actor;
import app.model.Movie;
import app.services.modelJSON.ActorJSON;
import app.services.modelJSON.MovieJSON;
import app.services.repository.Movies;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping("/actors")
public class ActorController {
    @GetMapping("/{actorId}")
    public ActorJSON getActor(@PathVariable long actorId, final HttpServletResponse response) throws IOException {
        try {
            return new ActorJSON(actorId);
        } catch (Exception e) {
            response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return null;
        }
    }


    @GetMapping("/movies")
    public List<MovieJSON> movies(@RequestBody String jsonString, final HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(jsonString, Properties.class);
        long actorId = Long.parseLong(properties.getProperty("actorId"));
        return Movies.getInstance().getMoviesList(ActorDB.getInstance().getActorById(actorId).getMovies());
    }
}
