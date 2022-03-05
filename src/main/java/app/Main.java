package app;

import app.Comment.Comment;
import app.Comment.CommentDB;
import app.actor.ActorController;
import app.actor.ActorDB;
import app.movie.MovieController;
import app.movie.MovieDB;
import app.user.UserController;
import app.user.UserDB;
import app.util.Path;
import app.util.ViewUtil;
import io.javalin.Javalin;

import java.io.IOException;

import static io.javalin.apibuilder.ApiBuilder.before;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;


public class Main {
    public static MovieDB movieDB;
    public static ActorDB actorDB;
    public static UserDB userDB;
    public static CommentDB commentDB;
    public static void main(String[] args) throws IOException {

        movieDB = new MovieDB();
        actorDB = new ActorDB();
        userDB = new UserDB();
        commentDB = new CommentDB();

        Javalin app = Javalin.create().start(4444);

        app.routes(() -> {
            get(Path.Web.WELCOME, ViewUtil.welcome);
            get(Path.Web.MOVIES, MovieController.fetchAllMovies);
            get(Path.Web.MOVIE, MovieController.fetchMovieById);
            get(Path.Web.ACTOR, ActorController.fetchActor);
            get(Path.Web.WATCHLIST, UserController.fetchWatchList);
            get(Path.Web.ADD_WATCHLIST, UserController.addToWatchList);
            get(Path.Web.RATE_MOVIE, UserController.rateMovie);
            get(Path.Web.VOTE_COMMENT, UserController.voteComment);
            get(Path.Web.MOVIE_SEARCH_YEAR, MovieController.fetchMoviesByYear);
            get(Path.Web.MOVIE_SEARCH_GENRE, MovieController.fetchMoviesByGenre);

            get(Path.Web.REMOVE_WATCHLIST, UserController.removeFromWatchList);
        });

        app.error(404, ViewUtil.notFound);
    }

}
