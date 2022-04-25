package app.services.modelJSON;

import app.db.MovieDB;
import app.model.Movie;

import java.io.IOException;

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
