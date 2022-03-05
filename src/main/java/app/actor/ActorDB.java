package app.actor;

import app.movie.Movie;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ActorDB {
    public static List<Actor> actors;
    public ActorDB() throws IOException {
        actors = getActorsArray("http://138.197.181.131:5000/api/actors");
    }
    public List<Actor> getActorsArray(String apiAddress) throws IOException{
        List<Actor> Actors = new ArrayList<>();
        URL url = new URL(apiAddress);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();
        int responsecode = con.getResponseCode();
        if (responsecode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responsecode);
        } else {
            String inline = "";
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                inline += scanner.nextLine();
            }
            scanner.close();
            Gson gson = new Gson();
            Actor[] actorArray = gson.fromJson(inline, Actor[].class);
            Actors = Arrays.asList(actorArray);
        }
        return Actors;
    }
    public Actor getActorById(long id) {
        return actors.stream().filter(a -> a.getId()== id).findFirst().orElse(null);
    }
}
