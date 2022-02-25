package com.ca01;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataManagerGetMovieByGenre {
    protected DataManager dataManager;
    protected JSONParser parser;

    @BeforeEach
    void setUp(){
        dataManager = new DataManager();
        parser = new JSONParser();
    }

    @Test
    void noMovieTest() throws ParseException {
        JSONObject obj = new JSONObject();
        JSONArray movieList = new JSONArray();
        String genreString = "{\"genre\": \"Action\"}";
        JSONObject genreObject = (JSONObject) parser.parse(genreString);
        obj.put("MoviesListByGenre", movieList);
        assertEquals(obj, dataManager.getMovieByGenre(genreObject));
    }
    @Test
    void getMovieByGenreTest() throws ParseException {
        JSONObject obj = new JSONObject();
        JSONArray movieList = new JSONArray();
        ArrayList<String> genres = new ArrayList<>();
        genres.add("Action");
        dataManager.movies.add(new Movie(1,"Movie1","","","",new ArrayList<>(),genres,new ArrayList<>(),0,0,0));
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("movieId", 1);
        jsonObj.put("name", "Movie1");
        jsonObj.put("director", "");
        JSONArray genresList = new JSONArray();
        genresList.add("Action");
        jsonObj.put("genres", genresList);
        jsonObj.put("rating", null);
        String genreString = "{\"genre\": \"Action\"}";
        JSONObject genreObject = (JSONObject) parser.parse(genreString);
        movieList.add(jsonObj);
        obj.put("MoviesListByGenre", movieList);
        //assertEquals(obj, dataManager.getMovieByGenre(genreObject));
    }

}
