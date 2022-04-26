package app.services.controllers;

import app.db.CommentDB;
import app.db.MovieDB;
import app.db.UserDB;
import app.model.Actor;
import app.model.Comment;
import app.model.Movie;
import app.model.User;
import app.services.modelJSON.ActorJSON;
import app.services.modelJSON.CommentJSON;
import app.services.modelJSON.MovieJSON;
import app.services.repository.Movies;
import app.tools.Response;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @GetMapping("/{movieId}")
    public MovieJSON getMovieById(@PathVariable long movieId, final HttpServletResponse response) throws IOException {
        try {
            return new MovieJSON(movieId);
        } catch (Exception e) {
            response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return null;
        }
    }

    @GetMapping("/cast/{movieId}")
    public List<ActorJSON> cast(@PathVariable long movieId, final HttpServletResponse response) throws IOException {
        return Movies.getInstance().getActorsList(MovieDB.getInstance().getMovieById(movieId).getCast());
    }

    public List<CommentJSON> getCommentList(List<Comment> comments) throws IOException {
        List<CommentJSON> commentsJSON = new ArrayList<>();
        for (Comment comment : comments) {
            commentsJSON.add(new CommentJSON(comment.getId()));
        }
        return commentsJSON;
    }

    @GetMapping("/comments/{movieId}")
    public List<CommentJSON> getComment(@PathVariable long movieId, final HttpServletResponse response) throws IOException {
        CommentDB.getInstance();
        Movie movie = MovieDB.getInstance().getMovieById(movieId);
        return getCommentList(movie.getComments());
    }

    @PostMapping(path = "/rate", consumes = "application/json", produces = "application/json")
    public String rate(@RequestBody(required = true) String jsonString, final HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(jsonString, Properties.class);
        long movieId = Long.parseLong(properties.getProperty("movieId"));

        int rate = Integer.parseInt(properties.getProperty("quantity"));
        Movie movie = MovieDB.getInstance().getMovieById(movieId);
        User user = UserDB.getInstance().getCurrentUser();
        if (rate > 0 && rate <= 10) {
            long prevRate = user.rateMovie(movieId, rate);
            long total = ((long) (movie.getRating() * movie.getRatingCount())) - prevRate;
            if(prevRate == 0){
                movie.incRatingCount();
            }
            movie.setRating((double) (total + rate) / (movie.getRatingCount()));
        }
        else {
            String error = "invalid rate value!";
            response.sendError(HttpStatus.BAD_REQUEST.value(), error);
            return null;
        }
        return Response.OK_RESPONSE;
    }

    @PostMapping(path = "/add", consumes = "application/json", produces = "application/json")
    public String add(@RequestBody(required = true) String jsonString, final HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(jsonString, Properties.class);
        long movieId = Long.parseLong(properties.getProperty("movieId"));
        Movie movie = MovieDB.getInstance().getMovieById(movieId);
        User user = UserDB.getInstance().getCurrentUser();
        try {
            if (user.getAge() > movie.getAgeLimit()) {
                user.addWatchList(movieId);
            } else {
                response.sendError(HttpStatus.BAD_REQUEST.value(), "Age Limit Error!");
                return null;
            }
        } catch (ParseException e) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return null;
        }
        return Response.OK_RESPONSE;
    }



    @PostMapping(path = "/comment", consumes = "application/json", produces = "application/json")
    public String comment(@RequestBody(required = true) String jsonString, final HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(jsonString, Properties.class);
        long movieId = Long.parseLong(properties.getProperty("movieId"));
        Movie movie = MovieDB.getInstance().getMovieById(movieId);
        String action = properties.getProperty("action");
        User user = UserDB.getInstance().getCurrentUser();

        switch (action) {
            case "comment":
                String comment = properties.getProperty("comment");
                Comment commentObj = new Comment(user.getEmail(), movieId, comment, CommentDB.getInstance().generateId());
                CommentDB.getInstance().addComment(commentObj);
                break;
            case "like": {
                int comment_id = Integer.parseInt(properties.getProperty("comment_id"));
                user.voteComment(movieId, comment_id, "like");
                break;
            }
            case "dislike": {
                int comment_id = Integer.parseInt(properties.getProperty("comment_id"));
                user.voteComment(movieId, comment_id, "dislike");
                break;
            }
        }

        return Response.OK_RESPONSE;
    }
}
