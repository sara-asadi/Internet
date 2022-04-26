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

    @GetMapping("/rate/{movieId}/{rate}")
    public String rate(@PathVariable long movieId,@PathVariable int rate , final HttpServletResponse response) throws IOException {
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

    @GetMapping("/add/{movieId}")
    public String add(@PathVariable long movieId, final HttpServletResponse response) throws IOException {
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

    @GetMapping("/comment/{movieId}/{text}")
    public String comment(@PathVariable long movieId,@PathVariable String text, final HttpServletResponse response) throws IOException {
        Movie movie = MovieDB.getInstance().getMovieById(movieId);
        User user = UserDB.getInstance().getCurrentUser();
        Comment commentObj = new Comment(user.getEmail(), movieId, text, CommentDB.getInstance().generateId());
        CommentDB.getInstance().addComment(commentObj);
        return Response.OK_RESPONSE;
    }
    @GetMapping("/like/{movieId}/{commentId}")
    public String likeComment(@PathVariable long movieId,@PathVariable int commentId, final HttpServletResponse response) throws IOException {
        Movie movie = MovieDB.getInstance().getMovieById(movieId);
        User user = UserDB.getInstance().getCurrentUser();
        user.voteComment(movieId, commentId, "like");
        return Response.OK_RESPONSE;
    }

    @GetMapping("/dislike/{movieId}/{commentId}")
    public String dislikeComment(@PathVariable long movieId,@PathVariable int commentId, final HttpServletResponse response) throws IOException {
        Movie movie = MovieDB.getInstance().getMovieById(movieId);
        User user = UserDB.getInstance().getCurrentUser();
        user.voteComment(movieId, commentId, "dislike");
        return Response.OK_RESPONSE;
    }
}
