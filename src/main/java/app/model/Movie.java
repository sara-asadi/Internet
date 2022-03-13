package app.model;

import app.db.ActorDB;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Movie {

    private long id;
    private String name;
    private String summary;
    private String releaseDate;
    private String director;
    private List<String> writers;
    private List<String> genres;
    private List<Long> cast;
    private double imdbRate;
    private long duration;
    private long ageLimit;
    private Double rating;
    private long ratingCount;
    private List<Comment> comments;

    public Movie(){
        writers = new ArrayList<>();
        genres = new ArrayList<>();
        cast = new ArrayList<>();
        rating = Double.valueOf(0);
        ratingCount = 0;
        comments = new ArrayList<>();
    }
    public  Movie(long id_, String name_, String summary_, String releaseDate_, String director_, List<String> writers_, List<String> genres_, List<Long> cast_, double imdbRate_, long duration_, long ageLimit_){
        id = (long) id_;
        (name = name_).equals("");
        (summary = summary_).equals("");
        (releaseDate = releaseDate_).equals("");
        (director = director_).equals("");
        writers = new ArrayList<>();
        writers.addAll(writers_);
        genres = new ArrayList<>();
        genres.addAll(genres_);
        cast = new ArrayList<>();
        cast.addAll(cast_);
        imdbRate = (double) imdbRate_;
        duration = (long) duration_;
        ageLimit = (long) ageLimit_;
        rating = Double.valueOf(0);
        ratingCount = 0;
        comments = new ArrayList<>();
    }
    public long getId() {
        return this.id;
    }
    public String getName(){
        return name;
    }
    public String getSummary() {return summary;}
    public String getReleaseDate() {return releaseDate;}
    public int getReleaseYear() {
        return Integer.parseInt(releaseDate.substring(0, 4));
    }
    public String getDirector(){
        return director;
    }
    public String getWriters(){
        return Arrays.toString(writers.toArray());
    }
    public String getGenres(){
        return Arrays.toString(genres.toArray());
    }
    public List<String> getGenresList() {return genres;}
    public List<Actor> getCast() throws IOException {
        List<Actor> aList = new ArrayList<>();
        for (Long aid : cast) {
            Actor a = ActorDB.getInstance().getActorById(aid);
            aList.add(a);
        }
        return aList;
    }
    public List<Long> getActors() {return cast;}
    public double getImdbRate() {
        return this.imdbRate;
    }
    public double getRating() {
        if (rating == null)
            return 0;
        return this.rating;
    }
    public long getDuration() {
        return this.duration;
    }
    public long getAgeLimit() {
        return this.ageLimit;
    }
    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }
    public void updateComment(long cid, Comment comment) {
        for (int i =0 ; i<comments.size(); i++){
            if(comments.get(i).getId() == cid){
                comments.set(i, comment);
            }
        }
    }
    public double getRatingCount() {
        return ratingCount;
    }

    public void setRating(double r) {
        rating = r;
    }

    public void incRatingCount() {
        ratingCount += 1;
    }

    public boolean isInPeriod(String start_year, String end_year) throws ParseException {
        Date rDate = new SimpleDateFormat("yyyy-MM-dd").parse(releaseDate);
        int releaseYear = rDate.getYear() + 1900;
        return releaseYear <= Integer.parseInt(end_year) && releaseYear >= Integer.parseInt(start_year);
    }
}
