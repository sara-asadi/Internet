package app.db;


import app.model.Actor;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ActorDB {
    private static ActorDB instance;
    public static List<Actor> actors;
    private ActorDB() throws IOException {
        actors = getActorsArray("http://138.197.181.131:5000/api/v2/actors");
    }
    public static ActorDB getInstance() throws IOException {
        if (instance == null)
            instance = new ActorDB();
        return instance;
    }
    public List<Actor> getActorsArray(String apiAddress) throws IOException{
        List<Actor> Actors = new ArrayList<>();
        URL url = new URL(apiAddress);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();
        int responseCode = con.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            StringBuilder inline = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }
            scanner.close();
            Gson gson = new Gson();
            Actor[] actorArray = gson.fromJson(inline.toString(), Actor[].class);
            Actors = Arrays.asList(actorArray);
        }
        return Actors;
    }
    public Actor getActorById(long id) {
        return actors.stream().filter(a -> a.getId()== id).findFirst().orElse(null);
    }
}
