package app.services.modelJSON;

import app.db.MovieDB;
import app.model.Actor;
import app.model.Movie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieJSON {
    private long id;
    private String name;
    private double imdbRate;
    private String image;
    private String summary;
    private String director;
    private long duration;
    private String writers;
    private String releaseDate;
    private Double rating;
    private Double ratingCount;
    private String coverImage;
    private List<String> actorNames;
    private List<String> actorImages;
    private List<Long> actorIds;
    private List<Integer> actorAges;

    public MovieJSON(long id_) throws IOException {
        Movie movie = MovieDB.getInstance().getMovieById(id_);
        id = id_;
        name = movie.getName();
        imdbRate = movie.getImdbRate();
        image = movie.getImage();
        summary = movie.getSummary();
        director = movie.getDirector();
        duration = movie.getDuration();
        writers = movie.getWriters();
        releaseDate = movie.getReleaseDate();
        rating = movie.getRating();
        ratingCount = movie.getRatingCount();
        coverImage = movie.getCoverImage();
        actorImages = getActorImages(movie.getCast());
        actorIds = getActorIds(movie.getCast());
        actorAges = getActorAges(movie.getCast());
        actorNames = getActorNames(movie.getCast());
    }

    public List<String> getActorImages(List<Actor> actors) {
        List<String> ss = new ArrayList<>();
        for (Actor actor : actors) {
            String s = actor.getImage();
            ss.add(s);
        }
        return ss;
    }
    public List<String> getActorNames(List<Actor> actors) {
        List<String> ss = new ArrayList<>();
        for (Actor actor : actors) {
            String s = actor.getName();
            ss.add(s);
        }
        return ss;
    }
    public List<Long> getActorIds(List<Actor> actors) {
        List<Long> ss = new ArrayList<>();
        for (Actor actor : actors) {
            Long s = actor.getId();
            ss.add(s);
        }
        return ss;
    }
    public List<Integer> getActorAges(List<Actor> actors) {
        List<Integer> ss = new ArrayList<>();
        for (Actor actor : actors) {
            Integer s = actor.getAge();
            ss.add(s);
        }
        return ss;
    }

    public List<String> getActorNames() {
        return actorNames;
    }

    public List<Integer> getActorAges() {
        return actorAges;
    }

    public List<Long> getActorIds() {
        return actorIds;
    }

    public List<String> getActorImages() {
        return actorImages;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getImdbRate() {
        return imdbRate;
    }

    public String getImage() {
        return image;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public String getSummary() {
        return summary;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getDirector() {
        return director;
    }

    public Double getRating() {
        return rating;
    }

    public Double getRatingCount() {
        return ratingCount;
    }

    public long getDuration() {
        return duration;
    }

    public String getWriters() {
        return writers;
    }
}
