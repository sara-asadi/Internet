package app.services.modelJSON;

import app.db.ActorDB;
import app.model.Actor;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActorJSON {
    private long id;
    private String name;
    private String birthDate;
    private String nationality;
    private int age;
    private int movieCount;

    public ActorJSON(long id_) throws IOException{
        Actor actor = ActorDB.getInstance().getActorById(id_);
        id = id_;
        name = actor.getName();
        birthDate = actor.getBirthDate();
        nationality = actor.getNationality();
        age = actor.getAge();
        movieCount = actor.getMovies().size();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    public int getAge() {
        return age;
    }

    public int getMovieCount() {
        return movieCount;
    }
}
