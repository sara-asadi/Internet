package com.ca01;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
        rating = null;
        ratingCount = 0;
        comments = new ArrayList<>();
    }
    public JSONObject getJsonObject() {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("movieId", id);
        jsonObj.put("name", name);
        jsonObj.put("director", director);
        JSONArray genresList = new JSONArray();
        for (String genre : genres)
            genresList.add(genre);
        jsonObj.put("genres", genresList);
        jsonObj.put("rating", rating);

        return jsonObj;
    }
    public JSONObject getJsonObject(DataManager handler) {
        JSONObject obj = new JSONObject();
        obj.put("movieId", id);
        obj.put("name", name);
        obj.put("summary", summary);
        obj.put("releaseDate", releaseDate);
        obj.put("director", director);
        obj.put("rating", rating);
        obj.put("duration", duration);
        obj.put("ageLimit", ageLimit);
        obj.put("writers", Arrays.toString(writers.toArray()));
        obj.put("genres", Arrays.toString(genres.toArray()));
        JSONArray casts = new JSONArray();
        for (Long actorId : cast)
            casts.add(handler.getActorById(actorId).getJsonObject());
        obj.put("cast", casts);
        JSONArray commentsList = new JSONArray();
        for (Comment comment : comments)
            commentsList.add(comment.getJsonObject());
        obj.put("comments", commentsList);

        return obj;
    }
    public boolean hasGenre(String genre) {
        for (String s : genres) if (Objects.equals(s, genre)) return true;
        return false;
    }
}
