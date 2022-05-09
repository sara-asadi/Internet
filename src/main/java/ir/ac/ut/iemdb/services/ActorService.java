package ir.ac.ut.iemdb.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ir.ac.ut.iemdb.model.Actor;
import ir.ac.ut.iemdb.repository.ActorRepository;
import ir.ac.ut.iemdb.services.HTTPRequestHandler.HTTPRequestHandler;
import ir.ac.ut.iemdb.tools.URL.Url;

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
        String ActorJsonString = HTTPRequestHandler.getRequest(Url.actors);
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
