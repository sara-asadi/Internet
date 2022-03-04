package app.movie;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    long id;
    String name;
    String summary;
    String releaseDate;
    String director;
    List<String> writers;
    List<String> genres;
    List<Long> cast;
    double imdbRate;
    long duration;
    long ageLimit;
    Double rating;
    long ratingCount;
//    List<Comment> comments;

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
        rating = null;
        ratingCount = 0;
//        comments = new ArrayList<>();
    }
}
