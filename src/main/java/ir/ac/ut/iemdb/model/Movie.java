package ir.ac.ut.iemdb.model;

import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Movie {
    //    id, name, summary, releaseDate, director, writers, genres, cast, imdbRate, duration, ageLimit, image, coverImage
    private Long id;
    private String name;
    private String summary;
    private Date releaseDate;
    private String director;
    private String writers;
    private List<String> genres;
    private List<String> cast;
    private Double imdbRate;
    private Integer duration;
    private Integer ageLimit;
    private Double rating;
    private Integer ratingCount;
    private String image;
    private String coverImage;

    public Movie(Long id, String name, String summary, Date releaseDate, String director, String writers, Double imdbRate,
                 Integer duration, Integer ageLimit, String image, String coverImage) {
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.releaseDate = releaseDate;
        this.director = director;
        this.writers = writers;
        this.genres = (ArrayList<String>) genres;
        this.cast = (ArrayList<String>) cast;
        this.imdbRate = imdbRate;
        this.duration = duration;
        this.ageLimit = ageLimit;
        rating = 0.0;
        ratingCount = 0;
        this.image = image;
        this.coverImage = coverImage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriters() {
        return writers;
    }

    public void setWriters(String writers) {
        this.writers = writers;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getCast() {
        return cast;
    }

    public void setCast(List<String> cast) {
        this.cast = cast;
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
