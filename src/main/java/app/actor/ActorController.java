package app.actor;

import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Handler;

import java.util.Map;

import static app.Main.actorDB;
import static app.Main.movieDB;

public class ActorController {

    public static Handler fetchActor = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("actor", actorDB.getActorById(Long.parseLong(ctx.pathParam("actor_id"))));
        ctx.render(Path.Template.ACTOR, model);
    };
}