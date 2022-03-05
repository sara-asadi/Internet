package app.movie;

import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Handler;

import java.util.Map;

import static app.Main.movieDB;
import static app.util.RequestUtil.getParamId;

public class MovieController {

    public static Handler fetchAllMovies = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("searchContext","");
        model.put("movies", movieDB.getAllMovies());
        ctx.render(Path.Template.MOVIES, model);
    };

    public static Handler fetchMovieById = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("movie", movieDB.getMovieById(Long.parseLong(ctx.pathParam("movie_id"))));
        ctx.render(Path.Template.MOVIE, model);
    };

    public static Handler fetchMoviesByYear = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("searchContext", " released from "+ctx.pathParam("start_year")+" to "+ctx.pathParam("end_year"));
        model.put("movies", movieDB.getMoviesByYear(ctx.pathParam("start_year"), ctx.pathParam("end_year")));
        ctx.render(Path.Template.MOVIES, model);
    };

    public static Handler fetchMoviesByGenre = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("searchContext", "in "+ctx.pathParam("genre")+" Genre");
        model.put("movies", movieDB.getMoviesByGenre(ctx.pathParam("genre")));
        ctx.render(Path.Template.MOVIES, model);
    };
}
