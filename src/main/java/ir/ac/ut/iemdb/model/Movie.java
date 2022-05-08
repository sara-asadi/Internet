package ir.ac.ut.iemdb.model;

import java.io.IOException;
import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Movie {
    //    id, name, summary, releaseDate, director, writers, genres, cast, imdbRate, duration, ageLimit, image, coverImage
    private int id;
    private String name;
    private String summary;
    private String releaseDate;
    private String director;
    private String writer;
    private Double imdbRate;
    private Integer duration;
    private Integer ageLimit;
    private Double rating;
    private Integer ratingCount;
    private String image;
    private String coverImage;

    private List<String> writers;
    private List<String> genres;
    private List<Integer> cast;
    private List<Comment> comments;

    public Movie() throws IOException {
        genres = new ArrayList<>();
        cast = new ArrayList<>();
        comments = new ArrayList<>();
    }

    public Movie(Integer id, String name, String summary, String  releaseDate, String director, String writer, Double imdbRate,
                 Integer duration, Integer ageLimit, String image, String coverImage) {
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.releaseDate = releaseDate;
        this.director = director;
        this.writer = writer;
        this.imdbRate = imdbRate;
        this.duration = duration;
        this.ageLimit = ageLimit;
        this.rating = 0.0;
        this.ratingCount = 0;
        this.image = image;
        this.coverImage = coverImage;
    }

    public Movie(Integer id, String name, String summary, String  releaseDate, String director, String writer, Double imdbRate,
                 Double rating, Integer ratingCount, Integer duration, Integer ageLimit, String image, String coverImage) {
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.releaseDate = releaseDate;
        this.director = director;
        this.writer = writer;
        this.imdbRate = imdbRate;
        this.duration = duration;
        this.ageLimit = ageLimit;
        this.rating = rating;
        this.ratingCount = ratingCount;
        this.image = image;
        this.coverImage = coverImage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public List<String> getWriters() {
        return writers;
    }

    public void setWriters(List<String> writers) {
        this.writers = writers;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<Integer> getCast() {
        return cast;
    }

    public void setCast(List<Integer> cast) {
        this.cast = cast;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Double getImdbRate() {
        return imdbRate;
    }

    public void setImdbRate(Double imdbRate) {
        this.imdbRate = imdbRate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(Integer ageLimit) {
        this.ageLimit = ageLimit;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}
