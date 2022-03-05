package app.user;

import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Handler;

import java.util.Map;

import static app.Main.*;

public class UserController {
    public static Handler fetchWatchList = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("user", userDB.getUserByEmail(ctx.pathParam("user_id")));
        model.put("watchList", userDB.getWatchList(ctx.pathParam("user_id")));
        ctx.render(Path.Template.WATCHLIST, model);
    };
    public static Handler addToWatchList = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        if (userDB.addToWatchList(ctx.pathParam("user_id"), Long.parseLong(ctx.pathParam("movie_id"))))
            ctx.render(Path.Template.SUCCESS, model);
        else
            ctx.render(Path.Template.FORBIDDEN, model);
    };
    public static Handler rateMovie = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        if (userDB.rateMovie(ctx.pathParam("user_id"), Long.parseLong(ctx.pathParam("movie_id")), Long.parseLong(ctx.pathParam("rate"))))
            ctx.render(Path.Template.SUCCESS, model);
        else
            ctx.render(Path.Template.FORBIDDEN, model);
    };
    public static Handler voteComment = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        if (userDB.voteComment(ctx.pathParam("user_id"), Long.parseLong(ctx.pathParam("comment_id")), Long.parseLong(ctx.pathParam("vote"))))
            ctx.render(Path.Template.SUCCESS, model);
        else
            ctx.render(Path.Template.FORBIDDEN, model);
    };

    public static Handler removeFromWatchList = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        if (userDB.removeFromWatchList(ctx.pathParam("user_id"), Long.parseLong(ctx.pathParam("movie_id"))))
            ctx.render(Path.Template.SUCCESS, model);
        else
            ctx.render(Path.Template.FORBIDDEN, model);
    };
    public static Handler addToWatchList_inForm = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        if (userDB.addToWatchList(ctx.pathParam("user_id"), Long.parseLong(ctx.pathParam("movie_id"))))
            ctx.render(Path.Template.SUCCESS, model);
        else
            ctx.render(Path.Template.FORBIDDEN, model);
    };
}
