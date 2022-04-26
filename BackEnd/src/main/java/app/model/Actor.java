package app.model;

import app.db.MovieDB;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public int getAge(){
        try {
            return 2022 - Integer.parseInt(birthDate.substring(birthDate.indexOf(',')+2, birthDate.length()));
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            return 0;
        }
    }

    public List<Movie> getMovies() throws IOException {
        List<Movie> mList = new ArrayList<>();
        for (Movie m : MovieDB.getInstance().getMovies())
            if (m.getActors().contains(id))
                mList.add(m);
        return mList;
    }
}