package ir.ac.ut.iemdb.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ir.ac.ut.iemdb.model.Actor;
import ir.ac.ut.iemdb.model.Cast;
import ir.ac.ut.iemdb.model.Comment;
import ir.ac.ut.iemdb.model.Actor;
import ir.ac.ut.iemdb.repository.ActorRepository;
import ir.ac.ut.iemdb.repository.CastRepository;
import ir.ac.ut.iemdb.repository.CommentRepository;
import ir.ac.ut.iemdb.services.HTTPRequestHandler.HTTPRequestHandler;

import java.util.List;

public class ActorService {

    private static ActorService instance;


    private ActorService() {
    }

    public static ActorService getInstance() {
        if (instance == null) {
            instance = new ActorService();
        }
        return instance;
    }

    public void importActorFromWeb() throws Exception {
        String ActorJsonString = HTTPRequestHandler.getRequest("http://138.197.181.131:5000/api/v2/actors");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Actor> actors = gson.fromJson(ActorJsonString, new TypeToken<List<Actor>>() {
        }.getType());

        for (Actor actor : actors) {
            try {
                ActorRepository.getInstance().insert(actor);
            } catch (Exception ignored) {}
        }
    }
}
