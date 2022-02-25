package com.ca01;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataManageAddToWatchListTest {

    protected DataManager dataManager;
    protected JSONParser parser;
    Comment comment;

    @BeforeEach
    void setUp(){
        dataManager = new DataManager();
        parser = new JSONParser();

        dataManager.users.add(new User("sara@ut.ac.ir","","","","2000-01-10"));
        dataManager.users.add(new User("sajjad@ut.ac.ir","","","","2010-01-10"));
        dataManager.movies.add(new Movie(1,"","","","",new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),0,0,15));
    }
    @Test
    void userAddMovieToWatchListTest() throws ParseException, java.text.ParseException {
        String watchListString = "{\"userEmail\": \"sara@ut.ac.ir\", \"movieId\": 1}";
        JSONObject watchListObject = (JSONObject) parser.parse(watchListString);
        dataManager.addToWatchList(watchListObject);
        assertEquals(1,dataManager.users.get(0).watchList.size());
    }

    @Test
    void addToWatchListUserNotFound() throws ParseException, java.text.ParseException{
        String watchListString = "{\"userEmail\": \"sara1@ut.ac.ir\", \"movieId\": 1}";
        JSONObject watchListObject = (JSONObject) parser.parse(watchListString);
        int error = dataManager.addToWatchList(watchListObject);
        assertEquals(-1, error);
    }

    @Test
    void addToWatchListMovieNotFound() throws ParseException, java.text.ParseException {
        String watchListString = "{\"userEmail\": \"sara@ut.ac.ir\", \"movieId\": 2}";
        JSONObject watchListObject = (JSONObject) parser.parse(watchListString);
        dataManager.addToWatchList(watchListObject);
        int error = dataManager.addToWatchList(watchListObject);
        assertEquals(-2, error);
        assertEquals(0, dataManager.users.get(0).watchList.size());
    }

    @Test
    void userUnderAgeAddMovieToWatchListTest() throws ParseException, java.text.ParseException {
        String watchListString = "{\"userEmail\": \"sajjad@ut.ac.ir\", \"movieId\": 1}";
        JSONObject watchListObject = (JSONObject) parser.parse(watchListString);
        int error = dataManager.addToWatchList(watchListObject);
        assertEquals(-3, error);
        assertEquals(0, dataManager.users.get(0).watchList.size());
    }

    @Test
    void userAddAlreadyAddedMovieToWatchListTest() throws ParseException, java.text.ParseException {
        dataManager.users.get(0).watchList.add(1L);

        String watchListString = "{\"userEmail\": \"sara@ut.ac.ir\", \"movieId\": 1}";
        JSONObject watchListObject = (JSONObject) parser.parse(watchListString);
        int error = dataManager.addToWatchList(watchListObject);
        assertEquals(-4, error);
        assertEquals(1, dataManager.users.get(0).watchList.size());
    }

}
