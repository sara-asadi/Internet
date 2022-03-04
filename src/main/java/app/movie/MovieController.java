package app.movie;

import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Handler;

import java.util.Map;

import static app.util.RequestUtil.getParamId;

public class MovieController {

    public static Handler fetchAllMovies = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("movies", MovieDao.getAllMovies());
        ctx.render(Path.Template.MOVIES_ALL, model);
    };

    public static Handler fetchOneBook = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("movie", MovieDao.getMovieById(getParamId(ctx)));
//        ctx.render(Path.Template.MOVIES_ONE, model);
    };
}
