package app.actor;

import app.movie.Movie;

import java.util.ArrayList;
import java.util.List;

import static app.Main.actorDB;
import static app.Main.movieDB;

public class Actor{
    private long id;
    private String name;
    private String birthDate;
    private String nationality;

    public Actor(){}
    public Actor(long id_, String name_, String birthDate_, String nationality_) {
        id = (long) id_;
        (name = name_).equals("");
        (birthDate = birthDate_).equals("");
        (nationality = nationality_).equals("");
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

    public List<Movie> getMovies() {
    List<Movie> mList = new ArrayList<>();
        for (Movie m : movieDB.getMovies())
            if (m.getActors().contains(id))
                mList.add(m);
        return mList;
}
}