package app.services.modelJSON;

import app.db.ActorDB;
import app.model.Actor;
import app.model.Movie;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActorJSON {
    private long id;
    private String name;
    private String birthDate;
    private String nationality;
    private int age;
    private int movieCount;
    private String image;
    private List<String> movieNames;
    private List<String> movieImages;
    private List<Long> movieIds;
    private List<Double> movieRatings;

    public ActorJSON(long id_) throws IOException{
        Actor actor = ActorDB.getInstance().getActorById(id_);
        id = id_;
        name = actor.getName();
        birthDate = actor.getBirthDate();
        nationality = actor.getNationality();
        age = actor.getAge();
        movieCount = actor.getMovies().size();
        image = actor.getImage();
        movieImages = getMovieImages(actor.getMovies());
        movieIds = getMovieIds(actor.getMovies());
        movieRatings = getMovieRatings(actor.getMovies());
        movieNames = getMovieNames(actor.getMovies());
    }

    public List<String> getMovieImages(List<Movie> movies) {
        List<String> ss = new ArrayList<>();
        for (Movie movie : movies) {
            String s = movie.getImage();
            ss.add(s);
        }
        return ss;
    }
    public List<String> getMovieNames(List<Movie> movies) {
        List<String> ss = new ArrayList<>();
        for (Movie movie : movies) {
            String s = movie.getName();
            ss.add(s);
        }
        return ss;
    }
    public List<Long> getMovieIds(List<Movie> movies) {
        List<Long> ss = new ArrayList<>();
        for (Movie movie : movies)
            ss.add(movie.getId());
        return ss;
    }
    public List<Double> getMovieRatings(List<Movie> movies) {
        List<Double> ss = new ArrayList<>();
        for (Movie movie : movies) {
            Double s = movie.getImdbRate();
            ss.add(s);
        }
        return ss;
    }

    public List<Double> getMovieRatings() {
        return movieRatings;
    }

    public List<String> getMovieNames() {
        return movieNames;
    }

    public List<Long> getMovieIds() {
        return movieIds;
    }

    public List<String> getMovieImages() {
        return movieImages;
    }

    public long getId() {
        return id;
    }

    public String getImage() {
        return image;
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
