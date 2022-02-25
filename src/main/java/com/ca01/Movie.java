package com.ca01;

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
    double rating;
    long ratingCount;
    List<Comment> comments;

    public  Movie(long id_, String name_, String summary_, String releaseDate_, String director_,
                  List<String> writers_, List<String> genres_, List<Long> cast_,
                  double imdbRate_, long duration_, long ageLimit_){
        id = id_;
        name = name_;
        summary = summary_;
        releaseDate = releaseDate_;
        director = director_;
        writers = writers_;
        genres = genres_;
        cast = cast_;
        imdbRate = imdbRate_;
        duration = duration_;
        ageLimit = ageLimit_;
        rating = 0;
        ratingCount = 0;
        comments = new ArrayList<>();
    }
}
